package hr.fer.zemris.java.hw16.web.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Servlet koji poslužuje zahtijeve vezane uz tag-ove slika.
 * 
 * @author Vedran Kolka
 *
 */
@Path("/tags")
public class Tags {

	/**
	 * Metoda dohvaća skup tag-ova svih slika i vraća u obliku json-a.
	 * 
	 * @return skup tag-ova svih slika i vraća u obliku json-a
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {

		Set<String> tags = DAOProvider.getDAO().getTags();
		if (tags == null || tags.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		JSONObject result = new JSONObject();
		JSONArray jsonTags = new JSONArray(tags);
		result.put("tags", jsonTags);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Metoda dohvaća imena svih slika čiji opisnik sadrži tag <code>tag</code> u
	 * obliku json-a.
	 * 
	 * @param tag imena svih slika čiji opisnik sadrži tag <code>tag</code> u obliku
	 *            json-a
	 * @return
	 */
	@Path("/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByTag(@PathParam("tag") String tag) {
		List<String> paths = DAOProvider.getDAO().getPictures(tag).stream().map(p -> p.getFilename())
				.collect(Collectors.toList());

		JSONObject result = new JSONObject();
		JSONArray jsonPaths = new JSONArray(paths);
		result.put("paths", jsonPaths);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

}
