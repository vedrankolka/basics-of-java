package hr.fer.zemris.java.hw16.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.model.PictureDescriptor;

/**
 * Servlet koji dohvaća opsinik slike.
 * 
 * @author Vedran Kolka
 *
 */
@Path("/image")
public class Image {

	/**
	 * Metoda iz sloja za perzistenciju dohvaća opisnik slike imena
	 * <code>fileName</code> u obliku json-a.
	 * 
	 * @param fileName ime tražene slike
	 * @return opisnik slike imena <code>fileName</code> u obliku json-a
	 */
	@GET
	@Path("/{fileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByName(@PathParam("fileName") String fileName) {

		PictureDescriptor picture = DAOProvider.getDAO().getPicture(fileName);

		if (picture == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		JSONObject result = new JSONObject();
		result.put("fileName", fileName);
		result.put("description", picture.getDescription());
		result.put("tags", new JSONArray(picture.getTags()));

		return Response.status(Status.OK).entity(result.toString()).build();
	}

}
