package ZZPJ.Project;
import junit.framework.Assert;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ArithmeticMeanTest {
  
  @Test
  public void calculateArithmeticMean() {
    ArithmeticMean mean = new ArithmeticMean();
    List<Integer> values = new ArrayList<Integer>();
    for(int i=1; i<=10; i++) {
      values.add( i );
    }
    Assert.assertEquals(5.0, mean.calculateArithmeticMean( values ));
  }

}
