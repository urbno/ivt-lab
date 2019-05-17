package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private GT4500 mockDA;
  private TorpedoStore tsprimary;
  private TorpedoStore tssecondary;
  @BeforeEach
  public void init(){
    tsprimary = mock(TorpedoStore.class);
    tssecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(tsprimary, tssecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(tsprimary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }


  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(tsprimary.fire(1)).thenReturn(true);
    when(tssecondary.fire(1)).thenReturn(true);
    when(tsprimary.isEmpty()).thenReturn(true);
    when(tssecondary.isEmpty()).thenReturn(true);
    // Act
    boolean result = !ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_primaryFired_secondaryIsEmpty_Success(){
    // Arrange
    when(tsprimary.fire(1)).thenReturn(true);
    when(tssecondary.fire(1)).thenReturn(true);
    when(tssecondary.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result &= ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(tsprimary, times(2)).fire(1);
    verify(tssecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_doubleShot_Success(){
    // Arrange
    when(tsprimary.fire(1)).thenReturn(true);
    when(tssecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result &= ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(tsprimary, times(1)).fire(1);
    verify(tssecondary, times(1)).fire(1);
  }

}
