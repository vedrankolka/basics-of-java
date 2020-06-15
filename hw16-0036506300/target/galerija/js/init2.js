/**
 * Probna skripta da vidimo jel radi ono s gumbima
 */
function getByTag(tagName) {
	alert(tagName);
}

function loadTags() {
	let tags = [ {
		tagName : 'gumb1'
	}, {
		tagName : 'gumb2'
	}, {
		tagName : 'gumb3'
	} ];

	let html = '';
	for (let i = 0; i < tags.length; i++) {
		let tag = htmlEscape(tags[i].tagName);
		let button = '<button name="' + tag
				+ '" onclick="getByTag(this.name);">' + tag + '</button>';
		html += button;
	}
	let div = document.getElementById("tagButtons");
	div.innerHTML = html;

}
