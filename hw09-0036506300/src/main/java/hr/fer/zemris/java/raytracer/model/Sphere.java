package hr.fer.zemris.java.raytracer.model;
/**
 * A model of a sphere that is a graphical object which can be set onto a scene.
 * @author Vedran Kolka
 *
 */
public class Sphere extends GraphicalObject {
	// center of the sphere
	private Point3D center;
	// radius of the sphere
	private double radius;
	// coefficient for the diffuse of the red component
	private double kdr;
	// coefficient for the diffuse of the green component
	private double kdg;
	// coefficient for the diffuse of the blue component
	private double kdb;
	// coefficient for the reflection of the red component
	private double krr;
	// coefficient for the reflection of the green component
	private double krg;
	// coefficient for the reflection of the blue component
	private double krb;
	// coefficient for the shininess of the spheres surface
	private double krn;
	
	/**
	 * 
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		// starting point of the ray
		double gx = ray.start.x;
		double gy = ray.start.y;
		double gz = ray.start.z;
		// components of the direction vector
		double vx = ray.direction.x;
		double vy = ray.direction.y;
		double vz = ray.direction.z;
		// center of the sphere
		double cx = center.x;
		double cy = center.y;
		double cz = center.z;
		/*
		 * the intersection is calculated by finding t that satisfies the equation
		 * where the distance from the point of intersection and the center of the sphere
		 * is equal to the radius of the sphere
		 * x = gx + t*vx
		 * y = gy + t*vy			radius^2 = (x - cx)^2 + (y - cy)^2 + (z - cz)^2
		 * z = gz + t*vz
		 */
		// calculating the coefficients for a quadratic equation a*t^2 + b*t + c = 0
		double a = vx*vx + vy*vy + vz*vz;
		double b = 2*(vx*(gx - cx) + vy*(gy - cy) + vz*(gz - cz));
		double c = (gx * gx - 2 * gx * cx + cx * cx) +
				   (gy * gy - 2 * gy * cy + cy * cy) +
		           (gz * gz - 2 * gz * cz + cz * cz) - radius * radius;
		
		double d = b * b - 4 * a * c;
		// if the determinant is negative, there is no intersection
		if(d < 0) {
			return null;
		}
		// solve for t
		double t1 = (-b - Math.sqrt(d)) / (2 * a);
		double t2 = (-b + Math.sqrt(d)) / (2 * a);
		/*
		 * if one of the solutions is negative, then the intersection is behind the point of view
		 * which means if the other is positive we are in the sphere which is illegal,
		 * or the sphere is behind the viewer, so the viewer does not see it
		 * TODO check if the viewer is allowed to be inside the sphere
		 */
		if (t1 < 0 || t2 < 0) {
			return null;
		}
		// the closer intersection is the one with the smaller step from the point of view (measured with t)
		double t = t1 < t2 ? t1 : t2;
		// calculate the point of closer intersection i = (ix, iy, iz)
		double ix = gx + t * vx;
		double iy = gy + t * vy;
		double iz = gz + t * vz;
		Point3D point = new Point3D(ix, iy, iz);
		
		double distance = point.sub(gx, gy, gz).norm();
		// the intersection is outer because the closer point of intersection is the point of entering
		return new SphereAndRayIntersection(point, distance, true);
	}
	/**
	 * A RayIntersection of a ray with a sphere.
	 * @author Vedran Kolka
	 *
	 */
	public class SphereAndRayIntersection extends RayIntersection {
		
		public SphereAndRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);	
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public Point3D getNormal() {
			return super.getPoint().sub(center).modifyNormalize();
		}
		
	}

}
