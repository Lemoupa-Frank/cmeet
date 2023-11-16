package camtrack.meet.role_record;

import java.util.HashMap;
import java.util.Map;

public class roles_record {
    private Map<String,String> data;

    public roles_record() {
        this.data = new HashMap<>();
        this.data.put("Human Resource Head","16se");
        this.data.put("CEO","1sdsd9v49dv4z9ef4-(-z");
        this.data.put("Directeur de l'informatique","cà&iéàcqcq4");
        this.data.put("Directeur Du Service Après-vente","16s46qsd4c1q6zdc6");
    }

    public String get_data(String key)
    {
        return data.get(key);
    }
}
