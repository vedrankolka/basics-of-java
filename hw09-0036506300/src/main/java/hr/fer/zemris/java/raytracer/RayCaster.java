package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * An implementation of a raytcaster used to trace a ray from the point of view
 * to a light source if it is possible, then determine the color of the reflected ray.
 * @author Vedran Kolka
 *
 */
public class RayCaster {
	/**
	 * The error to into consideration when calculating with doubles.
	 */
	public static final double DELTA = 1e-5;
	/**
	 * Coefficient for ambiental lighting.
	 */
	public static final double KA = 0.1;

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	public static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D OG = view.sub(eye).modifyNormalize();
				
				Point3D yAxis = viewUp.sub(OG.scalarMultiply(OG.scalarProduct(viewUp))).modifyNormalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				Point3D zAxis = xAxis.vectorProduct(yAxis).normalize();
				// calculate top left corner of the screen
				Point3D hor = xAxis.scalarMultiply(horizontal/2);
				Point3D ver = yAxis.scalarMultiply(vertical/2);
				Point3D corner = view.sub(hor).modifyAdd(ver);
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for (int y = 0 ; y < height; y++) {
					for (int x = 0 ; x < width; x++) {
						Point3D xScaled = xAxis.scalarMultiply(horizontal*x/(width - 1));
						Point3D yScaled = yAxis.scalarMultiply(vertical*y/(height - 1));
						Point3D screenPoint = corner.add(xScaled).modifySub(yScaled);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(zAxis, scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				
				// short[] red, short[] green, short[] blue, long requestVersions
				observer.acceptResult(red, green, blue, requestNo);
				
			}
		};
	}
	/**
	 * Traces a single ray to the closest graphical object if there is one.
	 * Traces the rays to all light sources to find if the object is shadowed.
	 * Calculates the rgb components for that reflected ray and writes them in
	 * the given <code>rgb</code> array. If the ray does not encounter a
	 * graphical object all rgb components are 0.
	 * @param v - normalized vector from the screen to the viewer
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	private static void tracer(Point3D v, Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest==null) {
			return;
		}
		
		calculateLighting(v, scene, closest, rgb);

	}
	/**
	 * Traces rays to each light source to check if the given <code>closest</code>
	 * intersection is shadowed by another graphical object.
	 * Then the rgb components are calculated.
	 * @param viewerRay - ray from the viewer to the object
	 * @param scene
	 * @param viewerAndObject
	 * @param rgb
	 */
	private static void calculateLighting(Point3D v, Scene scene, RayIntersection viewerAndObject, short[] rgb) {
		Point3D pointOnObject = viewerAndObject.getPoint();
		for(LightSource ls : scene.getLights()) {
			// add the ambiental lighting from this light source
			calcuateAmbientalLighting(ls, rgb);
			// create a ray from the light source
			Ray rayFromLightToObject = Ray.fromPoints(ls.getPoint(), pointOnObject);
			// finds the closest intersection from the light source to the object
			RayIntersection lightAndObject = findClosestIntersection(scene, rayFromLightToObject);
			// if an intersection that is closer to the light source was not found, the object is lit!
			if(lightAndObject.getDistance() + DELTA >= ls.getPoint().sub(pointOnObject).norm()) {
				Point3D n = lightAndObject.getNormal();
				Point3D l = ls.getPoint().sub(lightAndObject.getPoint());
				Point3D nNormalized = n.normalize();
				Point3D lNormalized = l.normalize();
				double reflectedRayScale = 2 * l.scalarProduct(nNormalized);
				Point3D r = nNormalized.scalarMultiply(reflectedRayScale).sub(l);
				calculateDefusedLighting(ls, lightAndObject, lNormalized, nNormalized, rgb);
				calculateReflectedLighting(ls, lightAndObject, r.normalize(), v.normalize(), rgb);
			}
			
		}
		
	}

	private static void calcuateAmbientalLighting(LightSource ls, short[] rgb) {
		rgb[0] += KA * ls.getR();
		rgb[1] += KA * ls.getG();
		rgb[2] += KA * ls.getB();
	}

	/**
	 * Calculates the reflective lighting components for the given intersection
	 *  Ir = Ii * ks * cos(alpha)^n where alpha is the angle between r and v.
	 * @param ls
	 * @param lightAndObject
	 * @param r
	 * @param v
	 * @param rgb
	 */
	private static void calculateReflectedLighting(LightSource ls, RayIntersection lightAndObject,
			Point3D r, Point3D v, short[] rgb) {
		double scale = Math.pow(r.scalarProduct(v), lightAndObject.getKrn());
		rgb[0] += (short) (ls.getR() * lightAndObject.getKrr() * scale);
		rgb[1] += (short) (ls.getG() * lightAndObject.getKrg() * scale);
		rgb[2] += (short) (ls.getB() * lightAndObject.getKrb() * scale);
		
	}

	/**
	 * Calculates the diffusion lighting components for the given intersection
	 *  Id = Ii * kd * cos(theta) where theta is the angle between the ray from the
	 *  light source and the normal of the surface.
	 * @param ls
	 * @param lightAndObject
	 * @param l
	 * @param n
	 * @param rgb
	 */
	private static void calculateDefusedLighting(LightSource ls, RayIntersection lightAndObject, 
			Point3D l, Point3D n, short[] rgb) {
		double cosTheta = n.scalarProduct(l);
		rgb[0] += (short) (ls.getR() * lightAndObject.getKdr() * cosTheta);
		rgb[1] += (short) (ls.getG() * lightAndObject.getKdg() * cosTheta);
		rgb[2] += (short) (ls.getB() * lightAndObject.getKdb() * cosTheta);
	}

	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		RayIntersection closest = null;
		
		for(GraphicalObject o : scene.getObjects()) {
			RayIntersection intersection = o.findClosestRayIntersection(ray);
			// if there is no intersection with the object, continue
			if (intersection == null) continue;
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
