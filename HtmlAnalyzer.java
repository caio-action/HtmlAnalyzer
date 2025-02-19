import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HtmlAnalyzer <URL>");
            System.exit(1);
        }

        try {
            HtmlFetcher fetcher = new HtmlFetcher();
            String htmlContent = fetcher.fetchHtmlFromUrl(args[0]);

            HtmlParser parser = new HtmlParser();
            HtmlParsingResult result = parser.parseHtml(htmlContent);

            if (result.isMalformed) {
                System.out.println("malformed HTML");
            } else if (result.deepestText != null) {
                System.out.println(result.deepestText.trim());
            }
        } catch (IOException e) {
            System.out.println("URL connection error");
        }
    }

    static class HtmlFetcher {
        public String fetchHtmlFromUrl(String url) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        }
    }

    static class HtmlParser {
        public HtmlParsingResult parseHtml(String html) {
            HtmlParsingResult result = new HtmlParsingResult();
            Deque<String> openTags = new ArrayDeque<>();
            String[] lines = html.split("\n");

            for (String rawLine : lines) {
                String line = rawLine.trim();
                if (line.isEmpty()) continue;

                if (isOpeningTag(line)) {
                    String tag = extractTagName(line);
                    if (tag == null || (openTags.isEmpty() && !"html".equalsIgnoreCase(tag))) {
                        result.markAsMalformed();
                        break;
                    }
                    openTags.push(tag);
                } else if (isClosingTag(line)) {
                    String expectedTag = openTags.pollFirst();
                    String actualTag = extractTagName(line);
                    if (expectedTag == null || !expectedTag.equals(actualTag)) {
                        result.markAsMalformed();
                        break;
                    }
                } else if (isTextLine(line)) {
                    int currentDepth = openTags.size();
                    if (currentDepth > result.maxDepth) {
                        result.updateDeepestText(line, currentDepth);
                    }
                } else {
                    result.markAsMalformed();
                    break;
                }
            }

            if (!openTags.isEmpty()) result.markAsMalformed();
            return result;
        }

        private boolean isOpeningTag(String line) {
            return line.matches("^<([a-zA-Z]+)>$");
        }

        private boolean isClosingTag(String line) {
            return line.matches("^</([a-zA-Z]+)>$");
        }

        private boolean isTextLine(String line) {
            return !line.startsWith("<") && !line.endsWith(">");
        }

        private String extractTagName(String line) {
            return line.replaceAll("[^a-zA-Z]", "");
        }
    }

    static class HtmlParsingResult {
        public String deepestText;
        public int maxDepth = -1;
        public boolean isMalformed = false;

        public void updateDeepestText(String text, int depth) {
            if (depth > maxDepth) {
                deepestText = text;
                maxDepth = depth;
            }
        }

        public void markAsMalformed() {
            isMalformed = true;
        }
    }
}