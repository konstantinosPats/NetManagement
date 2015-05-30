package files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import structs.Access_point;
import structs.Base_Station;
import structs.Battery;
import structs.Gps;


/**
 * Servlet implementation class FilesProcess
 */
@WebServlet("/FilesProcess")
public class FilesProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	/**
    *
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException
    * @throws SQLException
    */
    public FilesProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		ArrayList<Access_point> APs = new ArrayList<Access_point>();
		Access_point [] final_AP = null;
		
		ArrayList<Battery> Battery = new ArrayList<Battery>();
		Battery [] final_battery = null;
		
		ArrayList<Base_Station> baseSt = new ArrayList<Base_Station>();
		Base_Station [] final_baseSt = null;
		
		ArrayList<Gps> Gps = new ArrayList<Gps>();
		Gps [] final_Gps = null;
		
//		String csvFile = "/files/gps.csv";
		BufferedReader br = null;
		String line = "";
		String[] parts = null;
		int count = 0;
		try {
			InputStream wifi = FilesProcessServlet.class.getResourceAsStream("/files/wifi.csv");
			InputStream battery = FilesProcessServlet.class.getResourceAsStream("/files/battery.csv");
			InputStream base_station = FilesProcessServlet.class.getResourceAsStream("/files/base_station.csv");
			InputStream gps = FilesProcessServlet.class.getResourceAsStream("/files/gps.csv");
			
			//Access Point (wifi)
			br = new BufferedReader(new InputStreamReader(wifi));
			while ((line = br.readLine()) != null) {
				if(count!=0){
					Access_point tempAP = new Access_point();
					parts = line.split("\t");
					tempAP.setId(Integer.parseInt(parts[0]));
					tempAP.setUser(parts[1]);
					tempAP.setSSID(parts[2]);
					tempAP.setBSSID(parts[3]);
					tempAP.setRSSI(Integer.parseInt(parts[4]));
					tempAP.setFreq(Integer.parseInt(parts[5]));
					tempAP.setLat(Double.parseDouble(parts[6]));
					tempAP.setLon(Double.parseDouble(parts[7]));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    Date parsedDate = dateFormat.parse(parts[8]);
				    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
					tempAP.setTime(timestamp);
					APs.add(tempAP);
					
				}
				count++;
			}
			
			final_AP = new Access_point[count-1];
			
			for(int i=0; i<APs.size(); i++){
				final_AP[i] = APs.get(i);
			}
			count = 0;
			
			//Battery
			br = new BufferedReader(new InputStreamReader(battery));
			while ((line = br.readLine()) != null) {
				if(count!=0){
					Battery tempBat = new Battery();
					parts = line.split("\t");
					tempBat.setId(Integer.parseInt(parts[0]));
					tempBat.setUser(parts[1]);
					tempBat.setLevel(Integer.parseInt(parts[2]));
					tempBat.setPlugged(Integer.parseInt(parts[3]));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    Date parsedDate = dateFormat.parse(parts[6]);
				    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				    tempBat.setTime(timestamp);
					Battery.add(tempBat);
					
				}
				count++;
			}
			final_battery = new Battery[count-1];
			
			for(int i=0; i<Battery.size(); i++){
				final_battery[i] = Battery.get(i);
			}
			
			count = 0;
			//Base Station
			br = new BufferedReader(new InputStreamReader(base_station));
			while ((line = br.readLine()) != null) {
				if(count!=0){
					Base_Station tempBase = new Base_Station();
					parts = line.split("\t");
					tempBase.setId(Integer.parseInt(parts[0]));
					tempBase.setUser(parts[1]);
					tempBase.setOperator(parts[2]);
					tempBase.setMcc(Integer.parseInt(parts[3]));
					tempBase.setMnc(Integer.parseInt(parts[4]));
					tempBase.setCid(Integer.parseInt(parts[5]));
					tempBase.setLac(Integer.parseInt(parts[6]));
					if(parts[7].equals("No Latitude") || parts[7].equals("No latitude yet"))
						tempBase.setLat(-1);
					else
						tempBase.setLat(Double.parseDouble(parts[7]));
					if(parts[8].equals("No Latitude") ||parts[8].equals("No Longitude") || parts[8].equals("No longitude") || parts[8].equals("No longitude yet") || parts[8].equals("No latitude yet"))
						tempBase.setLon(-1);
					else{
						System.out.println(count + parts[8]);
						tempBase.setLon(Double.parseDouble(parts[8]));
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    Date parsedDate = dateFormat.parse(parts[9]);
				    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				    tempBase.setTime(timestamp);
					baseSt.add(tempBase);
					
				}
				count++;
			}
			final_baseSt = new Base_Station[count-1];
			
			for(int i=0; i<baseSt.size(); i++){
				final_baseSt[i] = baseSt.get(i);
			}
			
			count = 0;
			//GPS
			br = new BufferedReader(new InputStreamReader(gps));
			while ((line = br.readLine()) != null) {
				if(count!=0){
					Gps tempGps = new Gps();
					parts = line.split("\t");
					tempGps.setId(Integer.parseInt(parts[0]));
					tempGps.setUser(parts[1]);
					if(parts[2].equals("No Latitude") || parts[2].equals("No latitude yet") )
						tempGps.setLat(-1);
					else
						tempGps.setLat(Double.parseDouble(parts[2]));
					if(parts[3].equals("No Longitude") || parts[3].equals("No longitude") || parts[3].equals("No longitude yet")) 
						tempGps.setLon(-1);
					else
						tempGps.setLon(Double.parseDouble(parts[3]));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    Date parsedDate = dateFormat.parse(parts[4]);
				    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				    tempGps.setTime(timestamp);
					Gps.add(tempGps);
					
				}
				count++;
			}
			final_Gps = new Gps[count-1];
			
			for(int i=0; i<Gps.size(); i++){
				final_Gps[i] = Gps.get(i);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}  
		request.setAttribute("parse","ok");
 
		session.setAttribute("AP", final_AP);
		session.setAttribute("Battery", final_battery);
		session.setAttribute("Base_Station", final_baseSt);
		session.setAttribute("Gps", final_Gps);
		String url = "/index.jsp";
		RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url); 
        dispatcher.forward(request, response);
	}

}
