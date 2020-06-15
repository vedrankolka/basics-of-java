/**
 * Metoda koja ajax-om dohvaća sve tagove sa poslužitelja.
 * <p>
 * Koristi metodu htmlEscape definiranu u util.js .
 */
function loadTags() {
	$.ajax({

		url : 'rest/tags',
		dataType : 'json',

		success : function(data) {
			let tags = data.tags;
			let html = '';
			if (tags.length == 0) {
				html = 'Nema dostupnih tagova.';
			} else {
				for (let i = 0; i < tags.length; i++) {
					let tag = htmlEscape(tags[i]);
					let button = '<button class="tagButton" name="' + tag
							+ '" onclick="getByTag(this.name);">' + tag
							+ '</button>';
					html += button;
				}
			}
			$('#tagButtons').html(html);
		}
	});
}

/**
 * Metoda koja ajax-om dohvaća imena svih sličica (thumbnail-ova) s tag-om
 * <code>str</code> i dodaje ih u html dokument u čvor s identifikatorom
 * "thumbnails" pod img tag-om kako bi se sličice dohvatile s poslužitelja.
 * 
 * @param str -
 *            tag čije se slike žele dododati
 */
function getByTag(str) {
	let tag = htmlEscape(str);
	$
			.ajax({

				url : 'rest/tags/' + tag,
				dataType : 'json',

				success : function(data) {
					let paths = data.paths;
					let html = '';
					if (paths.length == 0) {
						html = 'Nema slika s tagom: ' + tag;
					} else {
						for (let i = 0; i < paths.length; i++) {
							let path = htmlEscape(paths[i]);
							let thumbnail = '<div class="thumbnailContainer"><button class="thumbnailButton"'
									+ ' name="'
									+ path
									+ '" onclick="getByName(this.name);">'
									+ '<img src="thumbnails?path='
									+ path
									+ '" alt="'
									+ path
									+ ' not found"></button></div>';

							html += thumbnail;
						}
					}
					$('#thumbnails').html(html);
					hidePicture();
				}

			});
}

/**
 * Metoda ajax-om dohvaća podatke o slici imena <code>str</code> s
 * poslužitelja i dodaje podatke u html dokument u čvorove s identifikatorima:
 * "picture", "pictureName", "description" i "pictureTags"
 * 
 * @param str -
 *            ime slike
 */
function getByName(str) {

	let fileName = htmlEscape(str);
	$.ajax({

		url : 'rest/image/' + str,
		dataType : 'json',

		success : function(picture) {

			let imgHtml = '<img src="image?fileName=' + fileName + '" alt="'
					+ fileName + ' not found.">';

			let description = htmlEscape(picture.description);
			let tags = picture.tags;
			let tagsHtml = '';
			for (let i = 0; i < tags.length; i++) {
				tagsHtml += '<span class="pictureTag">' + htmlEscape('#' + tags[i])
						+ '</span>';
			}

			$('#picture').html(imgHtml);
			$('#pictureName').html(fileName);
			$('#description').html(description);
			$('#pictureTags').html(tagsHtml);
			showPicture();
		}

	});
}

/**
 * Metoda skriva odeljak sa slikom, imenom slike, opisom i tagovima.
 */
function hidePicture() {
	let pictureDiv = document.getElementById("pictureParagraph");
	pictureDiv.style.display = "none";
}

/**
 * Metoda prikazuje odjeljak sa slikom, imenom slike, opisom i tagovima.
 */
function showPicture() {
	let pictureDiv = document.getElementById("pictureParagraph");
	pictureDiv.style.display = "block";
}








