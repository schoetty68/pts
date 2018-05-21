package pts;
import org.rrd4j.core.*;
import static org.rrd4j.DsType.*;
import static org.rrd4j.ConsolFun.*;




public class Main {
	public static void main(String[] args) {
		String rrdPath = "my.rrd";

		// first, define the RRD
		RrdDef rrdDef = new RrdDef(rrdPath, 300);
		rrdDef.addArchive(AVERAGE, 0.5, 1, 600); // 1 step, 600 rows
		rrdDef.addArchive(AVERAGE, 0.5, 6, 700); // 6 steps, 700 rows
		rrdDef.addArchive(MAX, 0.5, 1, 600);

		// then, create a RrdDb from the definition and start adding data
		RrdDb rrdDb = new RrdDb(rrdDef);
		Sample sample = rrdDb.createSample();
		while (...) {
		    double inbytes = ...
		    double outbytes = ...
		    sample.setValue("inbytes", inbytes);
		    sample.setValue("outbytes", outbytes);
		    sample.update();
		}
		//so ein Blödsin
		System.out.println("Hello World");
	}
}
