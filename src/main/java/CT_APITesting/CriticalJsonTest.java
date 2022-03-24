package CT_APITesting;

import java.util.List;

public class CriticalJsonTest {
    private  String page;
    private  String per_page;
    private  String total;
    private  String total_pages;
    private List<DataSet> data;
    private Ad ad;

    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }

    public String getPer_page() {
        return per_page;
    }
    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_pages() {
        return total_pages;
    }
    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<DataSet> getData() {
        return data;
    }
    public void setData(List<DataSet> data) {
        this.data = data;
    }

    public Ad getAd() {
        return ad;
    }
    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
