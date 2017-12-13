package org.dawnsci.surfacescatter;

public class CorrectionsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CorrectionsException( String s ) {
      super(s);
    }

//    public static void main(String[] args) throws CorrectionsException{
//        try {
//            throw new TestExceptions("If you see me, exceptions work!");
//        }
//        catch( Exception a ) {
//            System.out.println("Working Status: " + a.getMessage() );
//        }
//    }
}
