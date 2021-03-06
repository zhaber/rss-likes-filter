import javax.servlet.http.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonSlurper

class Servlet extends HttpServlet {
   void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
   resp.contentType = "text/xml"
        if (req.getLocalName().equals("127.0.0.1")) {
            resp.contentType = "text/plain"
        }
        resp.characterEncoding = "UTF-8"
        String rss = req.getParameter("rss")
        if (rss != null && !rss.isEmpty()) {
            try {
                int likes = req.getParameter("likes").toInteger()
                int maxTries = 3
                int urlTries = 0
                boolean urlSuccess = false
                while (!urlSuccess && urlTries < maxTries) {
                    try {
                        // the pipe is required to truncate feeds and convert from atom to rss
                        new URL(rss).withReader("UTF-8") { reader ->
                            def text = reader.text
                            def feed = new XmlParser().parseText(text)
                            def channel = feed.'channel'
                            channel.'item'.each {
                                String href = it.'link'.text()
                                HttpURLClient http = new HttpURLClient(url: "https://clients6.google.com/rpc?key=AIzaSyCKSbrvQasunBoV16zDH9R33D88CeLr9gQ")
                                int tries = 0
                                boolean success = false
                                while (!success && tries < maxTries) {
                                    try {
                                        HttpResponseDecorator response = http.request(
                                          method : 'POST',
                                          requestContentType : JSON,
                                          contentType : "application/json",
                                           body : [
                                              method : "pos.plusones.get",
                                              id : "p",
                                              params : [
                                                  nolog : true,
                                                  id : href,
                                                  source : "widget",
                                                  userId : "@viewer",
                                                  groupId : "@self"],
                                              jsonrpc : "2.0",
                                              key : "p",
                                              apiVersion : "v1"
                                          ]
                                      )
                                        if (response.data.result != null) {
                                            def entryLikes = response.data.result.metadata.globalCounts.'count'
                                            if (entryLikes < likes) {
                                                it.replaceNode {}
                                            }
                                      } else {
                                          it.replaceNode {}
                                        }
                                        success = true
                                    } catch (IOException e) {
                                        tries++
                                    }
                                }
                        }
                        new XmlNodePrinter(resp.writer).print(feed)
                            urlSuccess = true
                        }
                    } catch (IOException e) {
                      urlTries++
                  }
                }
            } catch (MalformedURLException e) {
                resp.writer.print e.getMessage()
            } catch (org.xml.sax.SAXParseException e){
                resp.writer.print "Wrong url: " + rss
            }
        }
    }
}
