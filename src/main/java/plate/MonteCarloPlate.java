package plate;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonteCarloPlate {

  private int width;
  private int height;
  List<List<Boolean>> table = new ArrayList<>();

  public MonteCarloPlate(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void setTable(List<List<Boolean>> table) {
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

  public boolean isExist(int x, int y) {
    return table.get(y).get(x);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<Boolean> row: table) {
      for(Boolean value: row){
        if(value == true){
          sb.append('*');
        } else{
          sb.append('-');
        }
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
