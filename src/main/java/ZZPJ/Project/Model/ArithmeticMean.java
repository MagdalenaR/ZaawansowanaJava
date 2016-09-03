package ZZPJ.Project.Model;

import java.util.List;

public class ArithmeticMean {
  
  public ArithmeticMean() {
    
  }
  
  public double calculateArithmeticMean(List<Integer> number) {
    int amount = 0;
    for(Integer num : number) {
      amount += num;
    }
    return amount/number.size( );
  }
  
}
