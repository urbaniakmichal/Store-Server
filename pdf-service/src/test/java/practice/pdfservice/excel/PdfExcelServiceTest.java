package practice.pdfservice.excel;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import practice.pdfservice.files.ExcelService;
import practice.pdfservice.rabbit.store.ConsumerStorePayload;
import practice.pdfservice.testData.TestData;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for pdf excel service")
@ExtendWith(MockitoExtension.class)
class PdfExcelServiceTest {

    private ExcelService excelService;

    private final String pathToDirectory = "src/test/java/practice/pdfservice/testfiles";
    private final String outputExcelPath = pathToDirectory + "/%s.xls";


    @BeforeEach
    void setUp() throws IOException {
        excelService = new ExcelService();
        setValueForFields();
        FileUtils.cleanDirectory(new File(pathToDirectory));
    }


    @DisplayName("Create xls with data from Store-Service")
    @Test
    void createExcelFile_basedOnDataFromStore_succeed() {
        // given
        String excelFileName = UUID.randomUUID().toString();
        ConsumerStorePayload consumerStorePayload = TestData.consumerStorePayload();
        consumerStorePayload.getOrderPdfDetails().setOrderUUID(excelFileName);

        String outputPath = String.format(outputExcelPath, excelFileName);


        // when
        excelService.createExcelFile(consumerStorePayload);


        // then
        String expectedName = String.format("%s.xls", excelFileName);
        assertEquals(expectedName, new File(outputPath).getName());
    }


    private void setValueForFields() {
        ReflectionTestUtils.setField(excelService, "sellerName", "sellerName");
        ReflectionTestUtils.setField(excelService, "sellerNip", "sellerNip");
        ReflectionTestUtils.setField(excelService, "sellerCity", "sellerCity");
        ReflectionTestUtils.setField(excelService, "sellerCityPostalCode", "sellerCityPostalCode");
        ReflectionTestUtils.setField(excelService, "sellerStreet", "sellerStreet");
        ReflectionTestUtils.setField(excelService, "sellerStreetNumber", "sellerStreetNumber");
        ReflectionTestUtils.setField(excelService, "outputExcelPath", outputExcelPath);
    }
}
