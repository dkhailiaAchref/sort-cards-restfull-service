package com.example.sortCartes;


import com.example.sortCartes.controller.CardsGameController;
import com.example.sortCartes.dao.BaseDAO;
import com.example.sortCartes.service.CardsGameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

/**
 * spring-aop (Aspect Oriented Programming ) + Génericité
 */
@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseTests {


    @InjectMocks
    public com.example.sortCartes.controller.CardsGameController cardsGameController;

    @InjectMocks
    public CardsGameService cardsGameService;

    @Autowired
    RestTemplate restTemplate;

    @InjectMocks
    BaseDAO baseDAO ;

    //injection des dependances ( arborecence des beans et properties à injecter dans les classes appellées dans le scope test)
    @Before
    @Test
    public void setup() {
        MockitoAnnotations.initMocks(this);

        CardsGameService vCardsGameService= this.unwrapProxy(cardsGameService);
        // y injecter des valeurs réelles pour :  fileCardsGamePath ,fileSortedCardsGamePath,baseDAO
        ReflectionTestUtils.setField(vCardsGameService, "baseDAO", this.baseDAO);
        ReflectionTestUtils.setField(vCardsGameService, "fileCardsGamePath", "src/test/resources/cardsGame.json");
        ReflectionTestUtils.setField(vCardsGameService, "fileSortedCardsGamePath", "src/test/resources/sortedCardsGame.json");

        CardsGameController vCardsGameController= this.unwrapProxy(cardsGameController);
        // y injecter des valeurs réelles pour :  cardsGameService
        ReflectionTestUtils.setField(vCardsGameController, "cardsGameService", this.cardsGameService);

    }
    //spring-aop (Aspect Oriented Programming )
    @SuppressWarnings("unchecked")
    public <T> T unwrapProxy(Object bean) {
        /*
         * If the given object is a proxy, set the return value as the object
         * being proxied, otherwise return the given object.
         */
        T vResult = null;
        try {
            if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
                Advised advised = (Advised) bean;
                vResult = (T) advised.getTargetSource().getTarget();
            } else {
                vResult = (T) bean;
            }
        } catch (Exception pEx) {
            // Test failed
            vResult = (T) bean;
        }
        assertNotNull(vResult);
        return vResult;
    }


}
