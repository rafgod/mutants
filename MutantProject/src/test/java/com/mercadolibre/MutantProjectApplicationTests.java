package com.mercadolibre;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;

import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.ResultMatcher;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MutantProjectApplicationTests {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    
    @Autowired    
    private MockMvc mockMvc;
    
    
    @Test
    @Order(1)
    public void consultMutantDNA() throws Exception {
	
	String json = "{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isOk());
    }   
    
    @Test
    @Order(2)
    public void consultHumanDNA() throws Exception {
	
	String json = "{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATTT\",\"AGACGG\",\"GCGTCA\",\"TCACTG\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isForbidden());
    }  
    
    @Test
    @Order(3)
    public void consultBadFormedDNA() throws Exception {
	
	String json = "{\"dna\": [\"ATGC\",\"CAGT\",\"TTATTT\",\"AGACGG\",\"GCGTCA\",\"TCACTG\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(4)
    public void consultBadFormedDNA_2() throws Exception {
	
	String json = "{\"dna\": [\"HHGFFH\",\"YYTTDY\",\"TTATTT\",\"AGACGG\",\"GCGTCA\",\"TCACTG\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(5)
    public void consultBadFormedDNA_3() throws Exception {
	
	String json = "{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATTT\",\"AGACGG\",\"GCGTCA\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(6)
    public void consultMutantDNA_2() throws Exception {
	
	String json = "{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"GGGGTA\",\"TCACTG\"]}";

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
        )
                .andExpect(status().isOk());
    }   
    
    @Test
    @Order(7)
    public void consultStats() throws Exception {	
        String res = "{\"count_mutant_dna\":2,\"count_human_dna\":1,\"ratio\":2.0}";
        
        assertTrue(
                ResultMatcher.matchJson(
                        mockMvc.perform(get("/stats"))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        res,
                        true
                )
        );
    }
   

}

