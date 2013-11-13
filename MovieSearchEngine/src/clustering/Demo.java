/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.clustering;

import java.io.*;
import java.util.*;
import moviesearchengine.tfidf.*;
import moviesearchengine.util.*;

/**
 *
 * @author flavius
 */
public class Demo {

    private static double computeTfIdf(int tf, int N, int df) {
        return tf * Math.log10(N / df); // tf * idf
    }

    private static double Sim(
            String docId1, String docId2,
            HashSet<String> terms1, HashSet<String> terms2,
            int N,
            HashMap<String, IndexEntry> termToIndexEntry) {

        double nominator = 0;
        double tmpDen = 0;
        for (String term : terms1) {
            IndexEntry ie = termToIndexEntry.get(term);
            int df = ie.getDf();
            double tfidf1 = computeTfIdf(ie.getTfOfDocId(docId1), N, df);
            if (terms2.contains(term)) {
                nominator += tfidf1 * computeTfIdf(ie.getTfOfDocId(docId2), N, df);
            }
            tmpDen += Math.pow(tfidf1, 2);
        }

        if (nominator == 0) {
            return 0;
        }

        double denominator = Math.sqrt(tmpDen);
        tmpDen = 0;
        for (String term : terms2) {
            IndexEntry ie = termToIndexEntry.get(term);
            tmpDen += Math.pow(computeTfIdf(ie.getTfOfDocId(docId2), N, ie.getDf()), 2);
        }
        denominator *= Math.sqrt(tmpDen);

        return nominator / denominator;
    }

    private static double Sim(double c_ji, double c_jm) {
        return Math.max(c_ji, c_jm); // single-link
    }

    // Figure 17.2 from the mandatory book
    private static Node HAC(
            HashMap<String, HashSet<String>> docIdToContentSet_title,
            HashMap<String, IndexEntry> termToIndexEntry_title,
            HashMap<String, HashSet<String>> docIdToContentSet_description,
            HashMap<String, IndexEntry> termToIndexEntry_description,
            HashMap<String, HashSet<String>> docIdToContentSet_genres,
            HashMap<String, IndexEntry> termToIndexEntry_genres,
            HashMap<String, HashSet<String>> docIdToContentSet_directors,
            HashMap<String, IndexEntry> termToIndexEntry_directors,
            HashMap<String, HashSet<String>> docIdToContentSet_actors,
            HashMap<String, IndexEntry> termToIndexEntry_actors) throws Exception {
        System.out.println("Starting HAC algorithm...");
        
        Object[] d = docIdToContentSet_title.keySet().toArray();

        int N = docIdToContentSet_title.size();

        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node((String) d[i]);
        }

        double[][] C = new double[N][N];
        boolean[] I = new boolean[N];

        // diagonal
        for (int n = 0; n < N; n++) {
            C[n][n] = 0;
        }

        // symmetric matrix
        for (int n = 0; n < N; n++) {
            for (int i = 0; i < n; i++) {
                String docId1 = (String) d[n];
                String docId2 = (String) d[i];
                double sim = 0.2 * Sim(
                        docId1, docId2,
                        docIdToContentSet_title.get(docId1),
                        docIdToContentSet_title.get(docId2),
                        N,
                        termToIndexEntry_title)
                        + 0.2 * Sim(
                        docId1, docId2,
                        docIdToContentSet_description.get(docId1),
                        docIdToContentSet_description.get(docId2),
                        N,
                        termToIndexEntry_description)
                        + 0.2 * Sim(
                        docId1, docId2,
                        docIdToContentSet_genres.get(docId1),
                        docIdToContentSet_genres.get(docId2),
                        N,
                        termToIndexEntry_genres)
                        + 0.2 * Sim(
                        docId1, docId2,
                        docIdToContentSet_directors.get(docId1),
                        docIdToContentSet_directors.get(docId2),
                        N,
                        termToIndexEntry_directors)
                        + 0.2 * Sim(
                        docId1, docId2,
                        docIdToContentSet_actors.get(docId1),
                        docIdToContentSet_actors.get(docId2),
                        N,
                        termToIndexEntry_actors);
                C[n][i] = sim;
                C[i][n] = sim;
            }
            I[n] = true;
        }

        Node node = new Node();
        for (int k = 1; k < N; k++) {
            double max = -1f / 0f;
            Merge merge = new Merge();
            for (int i = 0; i < N; i++) {
                if (I[i]) {
                    for (int m = 0; m < N; m++) {
                        if (I[m] && i != m && C[i][m] > max) {
                            max = C[i][m];
                            merge.setCluster1(i);
                            merge.setCluster2(m);
                        }
                    }
                }
            }

            int i = merge.getCluster1();
            int m = merge.getCluster2();

            node = new Node(nodes[i], nodes[m], C[i][m]);
            nodes[i] = node;

            for (int j = 0; j < N; j++) {
                if (j != i && j != m && I[j]) {
                    double sim = Sim(C[j][i], C[j][m]);
                    C[i][j] = sim;
                    C[j][i] = sim;
                }
            }

            I[m] = false;
        }

        System.out.println("Ending HAC algorithm.");
        return node;
    }
    private static int no = 0;
    private static ArrayList<String> ids;

    private static void writeClustersToFile(Node node) throws Exception {
        System.out.println("Starting writing clusters to file...");
        BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.OUTPUT_CLUSTERS_FILENAME));

        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        bw.newLine();

        bw.write("<clusters>");
        bw.newLine();

        writeClusters(bw, node);

        bw.write("</clusters>");
        bw.newLine();

        bw.flush();
        bw.close();

        System.out.println("Finished writing clusters to file " + Constants.OUTPUT_CLUSTERS_FILENAME);
    }

    private static void writeClusters(BufferedWriter bw, Node node) throws Exception {
        if (node != null) {
            if (node.getLeavesCount() <= Constants.MAXIMUM_CLUSTER_SIZE) {
                ids = new ArrayList<String>();
                collectIds(bw, node);
                if (!ids.isEmpty()) {
                    bw.write("<cluster no=\"" + no + "\">");
                    bw.newLine();

                    no++;

                    for (String id : ids) {
                        bw.write("<movie>" + id + "</movie>");
                        bw.newLine();
                    }

                    bw.write("</cluster>");
                    bw.newLine();
                }
            }
            writeClusters(bw, node.getLeft());
            writeClusters(bw, node.getRight());
        }
    }

    private static void collectIds(BufferedWriter bw, Node node) {
        if (node != null) {
            if (node.getId() != null && node.getLeft() == null && node.getRight() == null && node.getLeavesCount() == 1) {
                // leaf node
                if (!node.isVisited()) {
                    ids.add(node.getId());
                    node.setVisited(true);
                }
            } else if (node.getId() == null && node.getLeft() != null && node.getRight() != null && node.getLeavesCount() > 1) {
                // merge node
                collectIds(bw, node.getLeft());
                collectIds(bw, node.getRight());
            } else {
                throw new RuntimeException("Invalid tree node");
            }
        }
    }

    private static HashMap<String, ArrayList<String>> extractTitles(ArrayList<Movie> movies) {
        HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies) {
            ArrayList<String> content = new ArrayList<String>();
            content.add(movie.getTitle());
            docIdToContent.put(movie.getId(), content);
        }
        return docIdToContent;
    }

    private static HashMap<String, ArrayList<String>> extractDescriptions(ArrayList<Movie> movies) {
        HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies) {
            ArrayList<String> content = new ArrayList<String>();
            content.add(movie.getDescription());
            docIdToContent.put(movie.getId(), content);
        }
        return docIdToContent;
    }

    private static HashMap<String, ArrayList<String>> extractGenres(ArrayList<Movie> movies) {
        HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies) {
            ArrayList<String> content = new ArrayList<String>();
            content.add(movie.getGenres());
            docIdToContent.put(movie.getId(), content);
        }
        return docIdToContent;
    }

    private static HashMap<String, ArrayList<String>> extractDirectors(ArrayList<Movie> movies) {
        HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies) {
            ArrayList<String> content = new ArrayList<String>();
            content.add(movie.getDirectors());
            docIdToContent.put(movie.getId(), content);
        }
        return docIdToContent;
    }

    private static HashMap<String, ArrayList<String>> extractActors(ArrayList<Movie> movies) {
        HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies) {
            ArrayList<String> content = new ArrayList<String>();
            content.add(movie.getActors());
            docIdToContent.put(movie.getId(), content);
        }
        return docIdToContent;
    }

    // Convert from HashMap<String, ArrayList<String>> to HashMap<String, HashSet<String>>.
    // HashSet will not store duplicate terms.
    private static HashMap<String, HashSet<String>> convert(HashMap<String, ArrayList<String>> docIdToContent) {
        HashMap<String, HashSet<String>> docIdToContentSet = new HashMap<String, HashSet<String>>();
        for (Map.Entry<String, ArrayList<String>> entry : docIdToContent.entrySet()) {
            docIdToContentSet.put(entry.getKey(), new HashSet<String>(entry.getValue()));
        }
        docIdToContent.clear();
        return docIdToContentSet;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Movie> movies = SAXPartialDocExtraction.extractDocs(Constants.INPUT_XML_FILENAME);

        HashMap<String, ArrayList<String>> docIdToContent_title = extractTitles(movies);
        HashMap<String, ArrayList<String>> docIdToContent_description = extractDescriptions(movies);
        HashMap<String, ArrayList<String>> docIdToContent_genres = extractGenres(movies);
        HashMap<String, ArrayList<String>> docIdToContent_directors = extractDirectors(movies);
        HashMap<String, ArrayList<String>> docIdToContent_actors = extractActors(movies);
        movies.clear();

        docIdToContent_title = TfIdf.tokenizeDocs(docIdToContent_title, Constants.SIMPLE_ANALYZER);
        docIdToContent_description = TfIdf.tokenizeDocs(docIdToContent_description, Constants.STANDARD_ANALYZER);
        docIdToContent_genres = TfIdf.tokenizeDocs(docIdToContent_genres, Constants.SIMPLE_ANALYZER);
        docIdToContent_directors = TfIdf.tokenizeDocs(docIdToContent_directors, Constants.SIMPLE_ANALYZER);
        docIdToContent_actors = TfIdf.tokenizeDocs(docIdToContent_actors, Constants.SIMPLE_ANALYZER);

        HashMap<String, IndexEntry> termToIndexEntry_title = TfIdf.buildIndex(docIdToContent_title);
        HashMap<String, IndexEntry> termToIndexEntry_description = TfIdf.buildIndex(docIdToContent_description);
        HashMap<String, IndexEntry> termToIndexEntry_genres = TfIdf.buildIndex(docIdToContent_genres);
        HashMap<String, IndexEntry> termToIndexEntry_directors = TfIdf.buildIndex(docIdToContent_directors);
        HashMap<String, IndexEntry> termToIndexEntry_actors = TfIdf.buildIndex(docIdToContent_actors);

        HashMap<String, HashSet<String>> docIdToContentSet_title = convert(docIdToContent_title);
        HashMap<String, HashSet<String>> docIdToContentSet_description = convert(docIdToContent_description);
        HashMap<String, HashSet<String>> docIdToContentSet_genres = convert(docIdToContent_genres);
        HashMap<String, HashSet<String>> docIdToContentSet_directors = convert(docIdToContent_directors);
        HashMap<String, HashSet<String>> docIdToContentSet_actors = convert(docIdToContent_actors);

        Node node = HAC(
                docIdToContentSet_title,
                termToIndexEntry_title,
                docIdToContentSet_description,
                termToIndexEntry_description,
                docIdToContentSet_genres,
                termToIndexEntry_genres,
                docIdToContentSet_directors,
                termToIndexEntry_directors,
                docIdToContentSet_actors,
                termToIndexEntry_actors);

        writeClustersToFile(node);
    }
}
