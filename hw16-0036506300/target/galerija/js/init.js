/**
 * Metoda koja ajax-om dohvaća sve tagove sa poslužitelja.
 * <p>
 * Koristi metodu htmlEscape definiranu u htmlescaping.js .
 */

function loadTags() {
	$.ajax({

		url : 'rest/tagovi',
		dataType : 'json',
	
		success : function(data) {
			let tags = data;
			let html = '';
			if (tags.length == 0) {
				html = 'Nema dostupnih tagova.';
			} else {
				for (let i = 0; i < tags.length; i++) {
					let tag = htmlEscape(tags[i].tagName);
					let button = '<button name="' + tag
							+ '" onclick="getByTag(this.name);">' + tag
							+ '</button>';
					html += button;
				}
			}
			$('#tagButtons').html(html);
		}
	});
}