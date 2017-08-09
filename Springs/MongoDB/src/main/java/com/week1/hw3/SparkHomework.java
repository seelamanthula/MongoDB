
package com.week1.hw3;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class SparkHomework {
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                SparkHomework.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("answer.ftl");

                    Map<String, String> answerMap = new HashMap<String, String>();
                    answerMap.put("answer", createAnswer());

                    helloTemplate.process(answerMap, writer);
                } catch (Exception e) {
                    logger.error("Failed", e);
                    halt(500);
                }
                return writer;
            }
        });
    }

    // Create a silly answer that's not obvious just by code inspection.  Easier just to get it running!
    private static String createAnswer() {
        int i = 0;
        for (int bit = 0; bit < 16; bit++) {
            i |= bit << bit;
        }
        return Integer.toString(i);
    }
}
