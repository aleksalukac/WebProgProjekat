package uns.ac.rs.prodavnica.dto;

public class SearchDTO {

    private String param1;
    private String param2;
    private int sortByPrice = 0;

    public SearchDTO() {
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
    public int getSortByPrice() {
        return sortByPrice;
    }

    public void setSortByPrice(int sortByPrice) {
        this.sortByPrice = sortByPrice;
    }


}
