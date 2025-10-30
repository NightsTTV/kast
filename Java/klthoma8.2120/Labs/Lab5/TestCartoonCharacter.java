// JUNit 5 imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class TestCartoonCharacter 
{
	private CartoonCharacter rick;
	private CartoonCharacter morty;
		
	@BeforeEach
	public void setUp()
	{
		rick = new CartoonCharacter("Rick", 70, "Wubba lubba dub dub!\n");
		morty = new CartoonCharacter("Morty", 14, "Aaaaawwwww jeeeeeeezzzz..." + System.getProperty("line.separator"));
	}
	
	@Test
	public void testSerializeAndDeserializeRick(){
		CartoonCharacter character = null;
		CartoonCharacterSerializer.serializeCharacter(rick);
		character = CartoonCharacterSerializer.deserializeCharacter();
		assertEquals(character, rick);
	}

	@Test
	public void testSerializeAndDeserializeMorty(){
		CartoonCharacter character = null;
		CartoonCharacterSerializer.serializeCharacter(morty);
		character = CartoonCharacterSerializer.deserializeCharacter();
		assertEquals(character, morty);
	}

}
