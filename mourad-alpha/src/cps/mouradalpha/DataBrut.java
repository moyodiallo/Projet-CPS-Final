package cps.mouradalpha;

import java.util.ArrayList;
import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.MessageI;

/**
 * The class <code>DataBrut</code> is used to get an ArraList of random
 * <code>Footballeur</code>, it will be used for testing.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class DataBrut {
	/**
	 * 
	 * @return an ArrayList of random players
	 */
	public static ArrayList<Footballeur> data() {

		final String data_brut[][] = { { "joueur00", "club39", "31.5", "1.79", "47670", "108" },
				{ "joueur01", "club38", "37.5", "1.83", "32623", "117" },
				{ "joueur02", "club37", "31.5", "1.35", "44836", "20" },
				{ "joueur03", "club36", "25.5", "1.63", "49171", "76" },
				{ "joueur04", "club35", "29.5", "1.95", "33781", "140" },
				{ "joueur05", "club34", "33.5", "1.92", "21326", "134" },
				{ "joueur06", "club33", "32.5", "1.81", "28484", "113" },
				{ "joueur07", "club32", "31.5", "1.68", "36753", "87" },
				{ "joueur08", "club31", "37.5", "1.91", "35922", "132" },
				{ "joueur09", "club30", "29.5", "1.75", "33268", "101" },
				{ "joueur10", "club29", "32.5", "1.57", "45683", "159" },
				{ "joueur11", "club28", "30.5", "1.94", "47192", "65" },
				{ "joueur12", "club27", "34.5", "1.47", "29938", "139" },
				{ "joueur13", "club26", "28.5", "1.66", "35617", "45" },
				{ "joueur14", "club25", "33.5", "1.47", "31732", "82" },
				{ "joueur15", "club24", "27.5", "1.42", "39116", "45" },
				{ "joueur16", "club23", "29.5", "1.62", "49217", "34" },
				{ "joueur17", "club22", "27.5", "1.98", "49757", "168" },
				{ "joueur18", "club21", "37.5", "1.48", "27157", "74" },
				{ "joueur19", "club20", "26.5", "1.69", "36537", "146" },
				{ "joueur20", "club19", "34.5", "1.42", "24563", "190" },
				{ "joueur21", "club18", "37.5", "1.39", "29235", "164" },
				{ "joueur22", "club17", "29.5", "1.72", "24579", "47" },
				{ "joueur23", "club16", "33.5", "1.90", "44766", "89" },
				{ "joueur24", "club15", "36.5", "1.42", "23107", "35" },
				{ "joueur25", "club14", "37.5", "1.75", "40262", "196" },
				{ "joueur26", "club13", "34.5", "1.85", "46194", "28" },
				{ "joueur27", "club12", "27.5", "1.70", "28208", "185" },
				{ "joueur28", "club11", "29.5", "1.56", "49810", "94" },
				{ "joueur29", "club10", "26.5", "2.00", "37451", "130" },
				{ "joueur30", "club09", "36.5", "1.91", "43107", "153" },
				{ "joueur31", "club08", "26.5", "1.86", "46549", "35" },
				{ "joueur32", "club07", "35.5", "1.96", "39723", "100" },
				{ "joueur33", "club06", "30.5", "1.63", "49562", "121" },
				{ "joueur34", "club05", "32.5", "1.37", "24815", "90" },
				{ "joueur35", "club04", "33.5", "2.00", "30162", "166" },
				{ "joueur36", "club03", "26.5", "1.31", "23236", "173" },
				{ "joueur37", "club02", "30.5", "1.41", "43915", "62" },
				{ "joueur38", "club01", "31.5", "1.81", "22416", "151" },
				{ "joueur39", "club00", "30.5", "1.30", "49453", "132" }, };

		ArrayList<Footballeur> joueurs = new ArrayList<Footballeur>();
		for (int i = 0; i < data_brut.length; i++) {
			Footballeur footballeur = new Footballeur(data_brut[i][0], data_brut[i][1], Float.valueOf(data_brut[i][2]),
					Double.valueOf(data_brut[i][3]), Long.valueOf(data_brut[i][4]), Integer.valueOf(data_brut[i][5]),
					null);

			joueurs.add(footballeur);
		}

		return joueurs;
	}

	public static ArrayList<MessageFilterI> filters() {

		ArrayList<MessageFilterI> filters = new ArrayList<MessageFilterI>();

        filters.add(m -> {
            return m.getProprieties().getLongProp("tempsDeJeu") > 1000;
        });

        filters.add(m -> {
            return m.getProprieties().getLongProp("tempsDeJeu") > 4000;
        });

        filters.add(m -> {
            return m.getProprieties().getLongProp("tempsDeJeu") > 5000;
        });

        filters.add(m -> {
            return m.getProprieties().getFloatProp("vitesse") > 33 
                && m.getProprieties().getIntProp("nombreDeButMarque") > 100;
        });

        filters.add(m -> {
            return m.getProprieties().getFloatProp("vitesse") > 25 
                && m.getProprieties().getIntProp("nombreDeButMarque") > 62;
        });
        
		filters.add((MessageI m) -> {
			return m.getProprieties().getStringProp("nom").equals("joueur05");
		});

		filters.add(m -> {
			return m.getProprieties().getFloatProp("vitesse") > 30
					&& m.getProprieties().getIntProp("nombreDeButMarque") > 80;
		});

		filters.add(m -> {
			return m.getProprieties().getLongProp("tempsDeJeu") > 3000;
		});
		
		filters.add(m -> {
			return m.getProprieties().getDoubleProp("taille") > 1.80;
		});
		
		
		return filters;
	}
}