package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.CurtainsEntity;
import com.civilo.roller.repositories.CurtainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CurtainEntityTest {
    @Mock
    private CurtainsEntity curtainsEntityMock;

    @InjectMocks
    private CurtainsEntity curtainsEntity = new CurtainsEntity(1L, "type");

    @Test
    void testCurtainsEntity() {
        assertEquals(1L, curtainsEntity.getCurtainID());
        assertEquals("type", curtainsEntity.getCurtainType());
    }

    @Test
    void testCurtainsEntity2() {
        assertEquals(1L, curtainsEntity.getCurtainID());
        assertEquals("type", curtainsEntity.getCurtainType());
        CurtainsEntity curtainsEntity2 = new CurtainsEntity(2L, "type2");
        curtainsEntity2.setCurtainID(1L);
        curtainsEntity2.setCurtainType("type");
        assertEquals(curtainsEntity, curtainsEntity2);
        curtainsEntity.setCurtainType("newType");
        assertEquals("newType", curtainsEntity.getCurtainType());
    }

}
