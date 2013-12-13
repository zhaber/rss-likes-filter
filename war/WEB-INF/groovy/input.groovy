html.html {
	head { title "Google Reader Likes Filter" }
	body (style: "padding: 0px; margin: 0px auto;") {
		script("var like_keypress = false;" +
               "var default_rss = 'http://';" +
			   "var default_like = 10;" +
			   "function isValid() {" +
			   "   var rss = document.getElementById('rss').value;" +
			   "   var likes = document.getElementById('likes').value;" +
			   "   if (rss == 'http://' || rss == '' ) {alert ('Please enter valid Feed url'); return false};" +
			   "   if (likes == '') {alert ('Please enter valid number of +1'); return false}" +
			   "   return true;" +
			   "}");
		form (method : 'GET', action : '/getFeed', onsubmit: "return isValid()" ) {
			input(type: "hidden", name: "is_from_site", value: "1")
			table(border : "0", style:"position: absolute; top: 28%; width:100%; font-family: sans-serif") {
				tr() {
					td(style:"width:48%");
					td();
					td(style:"width:100%");
				}
				tr() {
					td();
					td (style: "border: 5px solid #c4c8cc; -webkit-border-radius: 15px; -moz-border-radius: 15px; padding: 20px; border-radius: 15px border") {
						table() {
							tr() {
								td(colspan:3, style:"text-align:center; padding-bottom: 15px"){
									p(style:"font-size:18px","Google Reader Likes Filter")
								}
							}
							tr() {
								td("Feed url:", style: "padding-bottom: 5px;");
								td(colspan: 2, style: "padding-bottom: 5px;") {
									input(id: "rss", type : "text", name: "rss", style:"width: 297px; margin-right: 3px", 
											onfocus: "if (this.value == default_rss) this.value = '';", 
											onblur: "if (this.value == '') this.value = default_rss", value: "http://")
								}
							}
							tr() {
								td() { yieldUnescaped 'Minimum&nbsp;likes:' }
								td(colspan: 2, valign: "center") {
									input(id:'likes', type : "text", name: "likes", value: 10, style:"width: 210px; float: left; margin: 0px 0px",
											onfocus: "if (this.value == '10' && !like_keypress) { this.value = ''; like_focus = true}",
											onblur: "if (this.value == '' && !like_keypress) this.value = default_like",
											onkeypress : "like_keypress = true;");
									input(type: "submit", value: "Get Feed", style: "margin: 0px 5px; width: 80px; padding: 0px;");
								}
							}
							tr() {
								td();
								td(colspan:2, align:"right", style: "padding-top: 11px") {
									span(style:"font-size:13px", "Created by");
									a(style: "font-size:13px", "Vitalii Fedorenko", href: "http://linkedin.com/in/myresume");
								}
							}
						}
					}
					td();
				}
			}
		}
		script("var _gaq = _gaq || [];" +
				"_gaq.push(['_setAccount', 'UA-18702814-1']); " +
				"_gaq.push(['_trackPageview']);"+
				"(function() {" +
				"	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;" +
				"ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';" +
				"	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);" +
				"})();")
	}
}