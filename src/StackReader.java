
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class StackReader {

    // ------------- BEGIN LOG4J------------------------

    private final static Logger logger = Logger.getLogger(StackReader.class);

    static {
        init();
    }

    private static void init() {
        DOMConfigurator.configure("log4j.xml");
    }

    // ------------- END LOG4J------------------------


    private BufferedReader cSVFileReader;
    private BufferedWriter out = null;
    private FileOutputStream fos = null;

    Set<DTOPontunVasar> allarpontunar = new HashSet<DTOPontunVasar>();
    ArrayList<String> vasaArray = new ArrayList<String>();


    public String SearchVasan(String enteredPontun) {

        //-------------BEGIN removing double entry and rewriting data to a file


        String pontunarN = null;
        String vasaN = null;

        DTOPontunVasar temp = null;
        try {
            cSVFileReader = new BufferedReader(new FileReader("VasaSkra.DAT"));
            String dataRow = cSVFileReader.readLine();

            while (dataRow != null) {
                String[] dataArray = dataRow.split(",");
                for (String item : dataArray) {
                    // TO DO make some error handling ( which comes from CSV) not null not empty
                    if (item.length() > 4 && item.length() < 7) {
                        pontunarN = item;
                    } else if (item.length() < 5) {
                        vasaN = item;

                    } else {
                    }
                    /*
                     * temp = new DTOPontunVasar(pontunarN, vasaN);
					 * allarpontunar.add(temp);
					 */
                    // System.out.print(item + "\t");
                }
                temp = new DTOPontunVasar(pontunarN, vasaN);
                allarpontunar.add(temp);
                System.out.println("vasa pontun " + vasaN + " " + pontunarN); // Print the data line.
                vasaN = null;
                pontunarN = null;
                System.out.println(); // Print the data line.
                dataRow = cSVFileReader.readLine();

                // get rid of a double entries here

            }
        } catch (IOException e) {
            logger.debug("reading a file -new sr- exception " + e.getMessage());
            // throw new IOException (e.getMessage());
        } finally {
            try {

                cSVFileReader.close();

            } catch (IOException e) {
                logger.debug("reading closing  a file -new sr- exception " + e.getMessage());
                e.printStackTrace();

            }

        }

        try {
            // Writing back a file without double entries

            fos = new FileOutputStream("VasaSkra.dat");
            out = new BufferedWriter(new OutputStreamWriter(fos));
            for (DTOPontunVasar pairToWrite : allarpontunar) {
                System.out.println("constructor writing from SET" + pairToWrite.getPontunarN() + " " + pairToWrite.getVasaN());
                String coma = ",";
                out.write((pairToWrite.getPontunarN()) == null ? "999999" : pairToWrite.getPontunarN());
                out.write(coma);
                out.write((pairToWrite.getVasaN()) == null ? "9999" : pairToWrite.getVasaN());
                out.write(coma);
                out.write("\n");

            }

            // Flush the BUFFER !!!

            // finally do close
        } catch (IOException e) {
            logger.debug("writing a file -new sr- exception " + e.getMessage());
            System.out.println(e);
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.debug("writing flush  a file -new sr- exception " + e.getMessage());
                System.out.println(e);
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e);
                logger.debug(" writing closing  a file -new sr- exception " + e.getMessage());
            }
        }


        //-------------END removing double entry and rewriting data to a file


        String foundVasaN = "";
        String tempVasaN = "";
        vasaArray.clear();
        for (DTOPontunVasar pairValue : allarpontunar) {

            if (enteredPontun.equalsIgnoreCase(pairValue.getPontunarN())) {

                tempVasaN = pairValue.getVasaN();

                vasaArray.add(tempVasaN);

            }

        }

        // remove, set is an overkill now
        Set<String> set = new HashSet<String>(vasaArray);

        System.out.print("Remove duplicate result ! : ");

        String[] result = new String[set.size()];
        set.toArray(result);
        for (String s : result) {
            String semicol = ";";
            System.out.print(s + ", ");
            // adding a "rekka msg to output for values bigger then 1000
            // Ispan marking rekkar from 1 to 2999 . available figures are
            // >=3000
            if (Integer.parseInt(s) <= 3000) {
                foundVasaN = foundVasaN.concat(" Rekka#" + s + semicol);

            } else if (Integer.parseInt(s) >= 6000) {// todo if for a big rack test
                foundVasaN = foundVasaN.concat(" EX St. Rekka#" + s + semicol);

            } else {
                foundVasaN = foundVasaN.concat(" inni-" + s.substring(1) + semicol);
            }
        }
        allarpontunar.clear();
        return foundVasaN;
    }

    public void DeletePontun(String pontunToDelete) {

        // --------------------------------------------BEGIN rereading file -----------------
        Set<DTOPontunVasar> deleteallarpontunar = new HashSet<DTOPontunVasar>();
        String pontunarN = null;
        String vasaN = null;

        DTOPontunVasar temporary = null;
        // deleteallarpontunar = null; Null point exception !f
        // cSVFileReader = null;
        try {
            cSVFileReader = new BufferedReader(new FileReader("VasaSkra.DAT"));
            String dataRow = cSVFileReader.readLine();
            // ArrayList <DTOPontunVasar> allarpontunar = new
            // ArrayList<DTOPontunVasar>();

            while (dataRow != null) {
                String[] dataArray = dataRow.split(",");
                for (String item : dataArray) {
                    // TO DO make some error handling ( which comes from CSV)
                    if (item.length() > 4 && item.length() < 7) {
                        pontunarN = item;
                    } else if (item.length() < 5) {
                        vasaN = item;

                    } else {
                    }
                    /*
                     * temp = new DTOPontunVasar(pontunarN, vasaN);
					 * allarpontunar.add(temp);
					 */
                    // System.out.print(item + "\t");
                }
                temporary = new DTOPontunVasar(pontunarN, vasaN);
                deleteallarpontunar.add(temporary);
                System.out.println("in DEL vasa pontun " + vasaN + " " + pontunarN); // Print the data line.
                vasaN = null;
                pontunarN = null;
                System.out.println(); // Print the data line.
                dataRow = cSVFileReader.readLine();

                // get rid of a double entries here

            }
        } catch (IOException e) {
            logger.debug("delete order READ exception" + e.getMessage());
            // throw new IOException (e.getMessage());
        } finally {
            try {

                cSVFileReader.close();


            } catch (IOException e) {
                logger.debug("delete order READ close exception" + e.getMessage());
                e.printStackTrace();

            }

        }


        // --------------------------------------------END  rereading file ------------------
        // todo compare a sizes of arrays allarpontunar.size();
        try {
            // FileOutputStream fos = null;

            fos = new FileOutputStream("VasaSkra.dat");
            out = new BufferedWriter(new OutputStreamWriter(fos));
            logger.debug("compare file changes (scanner upend) during delete " + (deleteallarpontunar.size() - allarpontunar.size()));
            for (DTOPontunVasar pairToWrite : deleteallarpontunar) {
                if (pontunToDelete.equalsIgnoreCase(pairToWrite.getPontunarN())) {
                    logger.debug("deleted order N : " + pontunToDelete);
                } else {

                    String coma = ",";
                    out.write((pairToWrite.getPontunarN()) == null ? "000000" : pairToWrite.getPontunarN());
                    out.write(coma);
                    out.write((pairToWrite.getVasaN()) == null ? "0000" : pairToWrite.getVasaN());
                    out.write(coma);
                    out.write("\n");
                }

            }

            // Flush the BUFFER !!!

            // finally do close
        } catch (IOException e) {
            logger.debug("delete order WRITE  exception" + e.getMessage());
            System.out.println(e);
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.debug("delete order WRITE FLUSH exception" + e.getMessage());
                System.out.println(e);
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                logger.debug("delete order WRITE CLOSE  exception" + e.getMessage());
                System.out.println(e);
            }
        }
    }
}
