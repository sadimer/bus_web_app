package DAO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionsDAOTest {
    private CompanyDAO company = new CompanyDAO();

    @Test
    void createExcept() {
        Assert.assertFalse(company.create(null));
    }

    @Test
    void deleteExcept() {
        Assert.assertFalse(company.delete(null));
    }

    @Test
    void updateExcept() {
        Assert.assertNull(company.update(null));
    }
}
