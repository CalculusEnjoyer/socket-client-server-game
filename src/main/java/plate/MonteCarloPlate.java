package plate;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonteCarloPlate {

  private int width;
  private int height;
  List<List<Integer>> table = new ArrayList<>();

  public MonteCarloPlate(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void setTable(List<List<Integer>> table) {
    if (table.size() != height) {
      throw new IllegalArgumentException("Invalid table height");
    }
    table.forEach(x -> {
      if (x.size() != width) {
        throw new IllegalArgumentException("Invalid table width");
      }
    });

    this.table = table;
  }

  public Integer isExist(int x, int y) {
    if (x > width || y > height || x < 0 || y < 0) {
      return 0;
    }
    return table.get(y).get(x);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<Integer> row : table) {
      for (Integer value : row) {
        if (value == 1) {
          sb.append('*');
        } else if (value == 0){
          sb.append('-');
        } else {
          sb.append("-");
        }
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
