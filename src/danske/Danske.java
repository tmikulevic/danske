package danske;
/**
 *
 * @author Tomas
 *
 */
public class Danske {

      public static void main(String[] args) {
    
    System.out.println("Answer is: " + getAnswer());
    
  }
  
//*******************************************************************************************

  public static int getAnswer() {
    int answer = 0;

    int line = 0;
    int column = 0;
    int compareNumber = 0;
    int countNumbersInLine = 0;
    boolean isEven;
    
    String text = "215\r\n" + 
                  "192 124\r\n" + 
                  "117 269 442\r\n" + 
                  "218 836 347 235\r\n" + 
                  "320 805 522 417 345\r\n" + 
                  "229 601 728 835 133 124\r\n" + 
                  "248 202 277 433 207 263 257\r\n" + 
                  "359 464 504 528 516 716 871 182\r\n" + 
                  "461 441 426 656 863 560 380 171 923\r\n" + 
                  "381 348 573 533 448 632 387 176 975 449\r\n" + 
                  "223 711 445 645 245 543 931 532 937 541 444\r\n" + 
                  "330 131 333 928 376 733 017 778 839 168 197 197\r\n" + 
                  "131 171 522 137 217 224 291 413 528 520 227 229 928\r\n" + 
                  "223 626 034 683 839 052 627 310 713 999 629 817 410 121\r\n" + 
                  "924 622 911 233 325 139 721 218 253 223 107 233 230 124 233";

    /*
     * splitting lines
     */
    String[] lines = text.split("\r\n");
    String[] allNumbersInXLine;
    /*
     * all numbers will be stored there and their values will be changed
     * */
    int[][] pyramid = new int[lines.length][lines.length];
    
    /*
     * same numbers stored for checking odd even sequence
     * */
    int[][] pyramidOriginal = new int[lines.length][lines.length];
    
    /*
     * loop through all lines
     * */
    for (String x : lines)
    {
      /*
       * splitting each line into symbols separated by space
       * */
      allNumbersInXLine = x.split(" ");
      
      /*
       * we need to know how many numbers in line
       * */
      countNumbersInLine = allNumbersInXLine.length;
      /*
       * loop through all numbers in one line
       * */
      for (String separateNumber : allNumbersInXLine) {
        /*
         * putting number as int into pyramid
         * */
        pyramid[line][column] = Integer.parseInt(separateNumber);
        pyramidOriginal[line][column] = Integer.parseInt(separateNumber);
        
        /*
         * the idea is to use Dijkstra algorithm we are going from top and 
         * adding the lowest number to the sum. In the end in bottom line we will have
         * the biggest sum
         * */
        
        /*
         * we start from checking second line
         * */
        if(line > 0) {
          /*
           * if it's first column then it have only number above
           * */
          if (column == 0) {
            if(checkEvenOdd(pyramidOriginal[line][column], pyramidOriginal[line-1][column])) {
              pyramid[line][column] = pyramid[line-1][column] + pyramid[line][column];

              answer = checkAnswer(answer, pyramid[line][column]);  
            }
            else {
              pyramidOriginal[line][column] = 0;
              pyramid[line][column] = 0;
            }
          }
          /*
           * if it's last number in line then it's have only diagonal upper number
           * */
          else if(column == countNumbersInLine - 1) {
            if(checkEvenOdd(pyramidOriginal[line][column], pyramidOriginal[line-1][column-1])) {
              pyramid[line][column] = pyramid[line-1][column-1] + pyramid[line][column];

              answer = checkAnswer(answer, pyramid[line][column]);
            }
            else {
              pyramidOriginal[line][column] = 0;
              pyramid[line][column] = 0;
            }
          }
          /*
           * if this diagonal number(by given example output result it can move only to right diagonal)
           * also checking if it's last number in line (then this number don't have above number)
           * */
          else if(column > 0 && countNumbersInLine - 1 > column) {
            compareNumber = 0;
            /*
             * we calculate compareNumber adding our number and number ABOVE
             * */
            if(checkEvenOdd(pyramidOriginal[line][column], pyramidOriginal[line-1][column])) {
              compareNumber = pyramid[line-1][column] + pyramid[line][column];
            }
            
            /*
             * we check if number sum of numbers in diagonal is bigger than compareNumber
             * */
            if(pyramid[line-1][column-1] + pyramid[line][column] > compareNumber) {
              /*
               * if yes, then we check even odd sequence then change compareNumber value to bigger value
               * */
              if(checkEvenOdd(pyramidOriginal[line][column], pyramidOriginal[line-1][column-1])) {
                compareNumber = pyramid[line-1][column-1] + pyramid[line][column];
              }
            }
            /*
             * we change current number to sum
             * */
            pyramid[line][column] = compareNumber;
            if(compareNumber == 0) {
              pyramidOriginal[line][column] = 0;
            }
            
            answer = checkAnswer(answer, pyramid[line][column]);
          }
        }
        column++;
      }
      /*
       * each line we start column index from 0
       * */
      column = 0;
      line++;
    }
    
    return answer;
  }
  
  private static int checkAnswer(int answer, int checkThisNumber) {
    /*
     * if it's the biggest number that we had then it's our answer
     * */
    if(answer < checkThisNumber) {
      return checkThisNumber;
    }
    return answer;
  }
  
  private static boolean checkEvenOdd(int number, int number2) {

    if(number == 0 || number2 == 0) {
      /*
       * if there is 0 value then return false
       * This help as stick to the sequence from top. if sequence is broken and number don't
       * have right number above it then it's value is equal to 0 and numbers below that 
       * won't start calculating sum from this number
       * (avoiding of problem if there will be got bigger sum calculating not from top number)
       * */
      return false;
    }
    else if((number % 2 == 0 && number2 % 2 != 0) || (number % 2 != 0 && number2 % 2 == 0)) {
      /*
       * check if one number is odd and other even, if yes - return true
       * */
      return true;
    }
    return false;
  }
}
