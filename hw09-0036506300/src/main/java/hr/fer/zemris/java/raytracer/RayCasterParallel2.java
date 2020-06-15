package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
/**
 * An implementation of a raycaster used to trace a ray from the point of view
 * to a light source if it is possible, then determine the color of the reflected ray.
 * @author Vedran Kolka
 *
 */
public class RayCasterParallel2 {
	
	private static Point3D xAxis;
	private static Point3D yAxis;
	private static Point3D zAxis;
	private static Point3D corner;
	private static Scene scene;

	/**
	 * The error to into consideration when calculating with doubles.
	 */
	public static final double DELTA = 1e-6;
	/**
	 * Coefficient for ambiental lighting.
	 */
	public static final double KA = 0.1;

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	private static IRayTracerProducer getIRayTracerProducer() {
		return new MyRayTracerProducer();
	}

	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 120; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10_000 * 2 * Math.PI;
				double t2 = (double) time / 5_000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}
	
	

	/**
	 * An implementation of a IRayProducer that uses a ForkJoinPool for
	 * parallelization of producing the rgb components for the screen.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	public static class MyRayTracerProducer implements IRayTracerProducer {

		@Override
		public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
				int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Calculation started...");

			short[] red = new short[width * height];
			short[] green = new short[width * height];
			short[] blue = new short[width * height];

			Point3D OG = view.sub(eye).modifyNormalize();

			yAxis = viewUp.sub(OG.scalarMultiply(OG.scalarProduct(viewUp))).modifyNormalize();
			xAxis = OG.vectorProduct(yAxis).normalize();
			zAxis = xAxis.vectorProduct(yAxis).normalize();
			// calculate top left corner of the screen
			Point3D hor = xAxis.scalarMultiply(horizontal / 2);
			Point3D ver = yAxis.scalarMultiply(vertical / 2);
			corner = view.sub(hor).modifyAdd(ver);

			scene = RayTracerViewer.createPredefinedScene2();

			ForkJoinPool pool = new ForkJoinPool();
			pool.invoke(new TracerJob(eye, horizontal, vertical, width, height, 0,
					height, cancel, red, green, blue));
			pool.shutdown();
			System.out.println("Calculation finished.");
			observer.acceptResult(red, green, blue, requestNo);
		}

	}

	/**
	 * A job of tracing rays and calculating rgb components of the screen pixels.
	 * The job is split recursively and its part of the screen is defined by the
	 * THRESHOLD.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	public static class TracerJob extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		// An acceptable number of rows for a single job to do
		public static final int THRESHOLD = 16;
		// Everything that defines the scene, viewer and screen
		private Point3D eye;
		private double horizontal;
		private double vertical;
		private int width;
		private int height;
		// part of the screen of this job
		private int yMin;
		private int yMax;
		// flag to check if the calculation has been canceled
		private AtomicBoolean cancel;
		// data to fill
		private short[] red;
		private short[] green;
		private short[] blue;

		/**
		 * Constructor for the job. Takes all parameters of the scene and screen needed
		 * to do its part of the job.
		 * 
		 * @param eye
		 * @param xAxis
		 * @param yAxis
		 * @param zAxis
		 * @param corner
		 * @param scene
		 * @param horizontal
		 * @param vertical
		 * @param width      of the screen
		 * @param height     of the screen
		 * @param yMin       - part of the screen where this job starts
		 * @param yMax       - part of the screen where this job ends
		 * @param cancel     - AtomicBoolean to cancel the job
		 * @param red        array of shorts to fill
		 * @param green      array of shorts to fill
		 * @param blue       array of shorts to fill
		 */
		public TracerJob(Point3D eye,
				double horizontal, double vertical, int width, int height, int yMin, int yMax, AtomicBoolean cancel,
				short[] red, short[] green, short[] blue) {
			super();
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.cancel = cancel;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		public void compute() {

			if (yMax - yMin + 1 <= THRESHOLD) {
				computeDirect();
				return;
			}
			// split the stripe in two
			int yMax1 = yMin + (yMax - yMin) / 2;
			invokeAll(
					new TracerJob(eye, horizontal, vertical, width, height, yMin,
							yMax1, cancel, red, green, blue),
					new TracerJob(eye, horizontal, vertical, width, height, yMax1,
							yMax, cancel, red, green, blue));

		}

		public void computeDirect() {

			short[] rgb = new short[3];
			int offset = width * yMin;

			for (int y = yMin; y < yMax; y++) {
				// if the job was canceled, return
				if (cancel.get())
					return;
				
				Point3D yScaled = yAxis.scalarMultiply(vertical * y / (height - 1));
				for (int x = 0; x < width; x++) {

					Point3D xScaled = xAxis.scalarMultiply(horizontal * x / (width - 1));
					Point3D screenPoint = corner.add(xScaled).modifySub(yScaled);

					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(zAxis, scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}
	}

	/**
	 * Traces a single ray to the closest graphical object if there is one. Traces
	 * the rays to all light sources to find if the object is shadowed. Calculates
	 * the rgb components for that reflected ray and writes them in the given
	 * <code>rgb</code> array. If the ray does not encounter a graphical object all
	 * rgb components are 0.
	 * 
	 * @param v
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	private static void tracer(Point3D v, Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}

		calculateLighting(v, scene, closest, rgb);

	}

	/**
	 * Traces rays to each light source to check if the given <code>closest</code>
	 * intersection is shadowed by another graphical object. Then the rgb components
	 * are calculated.
	 * 
	 * @param viewerRay       - ray from the viewer to the object
	 * @param scene
	 * @param viewerAndObject
	 * @param rgb
	 */
	private static void calculateLighting(Point3D v, Scene scene, RayIntersection viewerAndObject, short[] rgb) {
		Point3D pointOnObject = viewerAndObject.getPoint();
		for (LightSource ls : scene.getLights()) {
			// add the ambiental lighting from this light source
			calcuateAmbientalLighting(ls, rgb);
			// create a ray from the light source
			Ray rayFromLightToObject = Ray.fromPoints(ls.getPoint(), pointOnObject);
			// finds the closest intersection from the light source to the object
			RayIntersection lightAndObject = findClosestIntersection(scene, rayFromLightToObject);
			// if it is null, there was an error because of the double precision
			if(lightAndObject == null) {
				return;
			}
			// if an intersection that is closer to the light source was not found, the
			// object is lit!
			if (lightAndObject.getDistance() + DELTA >= ls.getPoint().sub(pointOnObject).norm()) {
				Point3D n = lightAndObject.getNormal();
				Point3D l = ls.getPoint().sub(lightAndObject.getPoint());
				Point3D nNormalized = n.normalize();
				Point3D lNormalized = l.normalize();
				double reflectedRayScale = 2 * l.scalarProduct(nNormalized);
				Point3D r = nNormalized.scalarMultiply(reflectedRayScale).sub(l).modifyNormalize();
				calculateDefusedLighting(ls, lightAndObject, lNormalized, nNormalized, rgb);
				calculateReflectedLighting(ls, lightAndObject, r, v, rgb);
			}

		}

	}

	private static void calcuateAmbientalLighting(LightSource ls, short[] rgb) {
		rgb[0] += KA * ls.getR();
		rgb[1] += KA * ls.getG();
		rgb[2] += KA * ls.getB();
	}

	/**
	 * Calculates the reflective lighting components for the given intersection Ir =
	 * Ii * ks * cos(alpha)^n where alpha is the angle between r and v.
	 * 
	 * @param ls
	 * @param lightAndObject
	 * @param r
	 * @param v
	 * @param rgb
	 */
	private static void calculateReflectedLighting(LightSource ls, RayIntersection lightAndObject, Point3D r, Point3D v,
			short[] rgb) {
		double scale = Math.pow(r.scalarProduct(v), lightAndObject.getKrn());
		rgb[0] += (short) (ls.getR() * lightAndObject.getKrr() * scale);
		rgb[1] += (short) (ls.getG() * lightAndObject.getKrg() * scale);
		rgb[2] += (short) (ls.getB() * lightAndObject.getKrb() * scale);

	}

	/**
	 * Calculates the diffusion lighting components for the given intersection Id =
	 * Ii * kd * cos(theta) where theta is the angle between the ray from the light
	 * source and the normal of the surface.
	 * 
	 * @param ls
	 * @param lightAndObject
	 * @param l
	 * @param n
	 * @param rgb
	 */
	private static void calculateDefusedLighting(LightSource ls, RayIntersection lightAndObject, Point3D l, Point3D n,
			short[] rgb) {
		double cosTheta = n.scalarProduct(l);
		rgb[0] += (short) (ls.getR() * lightAndObject.getKdr() * cosTheta);
		rgb[1] += (short) (ls.getG() * lightAndObject.getKdg() * cosTheta);
		rgb[2] += (short) (ls.getB() * lightAndObject.getKdb() * cosTheta);
	}

	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {

		RayIntersection closest = null;

		for (GraphicalObject o : scene.getObjects()) {
			RayIntersection intersection = o.findClosestRayIntersection(ray);
			// if there is no intersection with the object, continue
			if (intersection == null)
				continue;
			// if an intersection has yet not been found, this is the closest one
			if (closest == null) {
				closest = intersection;
			} else {
				// else check which intersection is closer
				closest = intersection.getDistance() < closest.getDistance() ? intersection : closest;
			}
		}

		return closest;
	}

}
