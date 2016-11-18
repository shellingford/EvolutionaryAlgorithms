package evoalg.examples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.java.Log;

/**
 * Parses TSP GEOM type file.
 *
 * File type specification for GEOM and other types can be found here:
 * http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/tsp95.pdf
 */
@Log
public class TspFileParser {

  @Getter
  private int pointCount;

  /**
   * Creates distance map with all possible point combinations and their distances.
   *
   * @param fileName file name with point data
   * @return map of all possible point combinations and their distances
   */
  public Map<String, Integer> createDistanceMap(String fileName) {
    List<TSPPoint> points;
    try {
      points = extractGeomPointsFromFile(fileName);
    }
    catch (URISyntaxException | IOException e) {
      log.severe("There was a problem with reading TSP file: " + e);
      return new HashMap<>();
    }

    //pointCount will be used as genotype (permutation) size
    pointCount = points.size();
    return createDistanceMapFromPoints(points);
  }

  /**
   * Makes a map from list of points where key is combination of ids of two points
   * and value is Euclidean distance between those two points. Every possible
   * distinct (id1-id2 and id2-id1 combinations are the same) combination of two
   * points and their distances will be calculated.
   *
   * @param points list of points
   * @return map of every possible distance between two points
   */
  public Map<String, Integer> createDistanceMapFromPoints(List<TSPPoint> points) {
    Map<String, Integer> distanceMap = new HashMap<>();
    for (int i = 0; i < points.size() - 1; i++) {
      for (int j = i + 1; j < points.size(); j++) {
        TSPPoint point1 = points.get(i);
        TSPPoint point2 = points.get(j);

        String key = distanceMapKey(point1.getId(), point2.getId());
        int value = distance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        distanceMap.put(key, value);
      }
    }
    return distanceMap;
  }

  /**
   * Extracts all geometric points from TSP file with their ids as list of TSPPoint objects.
   *
   * @param fileName tsp file name
   * @return list of TSPPoint objects that represent geometric points from the file
   * @throws URISyntaxException
   * @throws IOException
   */
  private List<TSPPoint> extractGeomPointsFromFile(String fileName) throws URISyntaxException, IOException {
    List<TSPPoint> points = new ArrayList<>();
    //regex for lines only with geometric points
    String regex = "^[0-9]+ [-+]?[0-9]*\\.?[0-9]+ [-+]?[0-9]*\\.?[0-9]+$";
    Path filePath = getPathFromFileName(fileName);
    try (Stream<String> stream = Files.lines(filePath)) {
      stream.filter(line -> line.matches(regex))
            .forEach(line -> {
              String[] tokens = line.split(" ");

              //ids should start at 0, because permutation uses numbers from 0 to size - 1
              int id = Integer.parseInt(tokens[0]) - 1;
              double x = Double.parseDouble(tokens[1]);
              double y = Double.parseDouble(tokens[2]);

              points.add(new TSPPoint(id, x, y));
            });
    }
    return points;
  }

  /**
   * Creates Path object from specified fileName.
   *
   * @param fileName file name
   * @return Path object for specified fileName
   * @throws URISyntaxException
   */
  private Path getPathFromFileName(String fileName) throws URISyntaxException {
    URI uri = getClass().getResource("data/tsp/wi29.tsp").toURI();
    return Paths.get(uri);
  }

  /**
   * Creates string that is combination of two point ids.
   * For example:
   *  id1 = 1
   *  id2 = 2
   *  resulting string = "1,2"
   *
   * By default (because of outer loops) this rule will be followed: id1 < id2
   *
   * @param id1 point1 id
   * @param id2 point2 id
   * @return string as "id1,id2"
   */
  private String distanceMapKey(int id1, int id2) {
    return id1 + "," + id2;
  }

  /**
   * Calculates Euclidean distance between two points with specified coordinates.
   *
   * @param x1 x coordinate of point1
   * @param y1 y coordinate of point1
   * @param x2 x coordinate of point2
   * @param y2 y coordinate of point2
   * @return Euclidean distance between two points
   */
  private int distance(double x1, double y1, double x2, double y2) {
    double xd = x1 - x2;
    double yd = y1 - y2;
    return (int) (Math.sqrt(xd * xd + yd * yd) + 0.5);
  }
}
