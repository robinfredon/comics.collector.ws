package cfranc.com.comics.collector.ws.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Query;
import cfranc.com.comics.collector.ws.model.Album;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlbumRepositoryTest {

	@Autowired
	private TestEntityManager em;
	
	@Test
	public void testFindAllSort() {
	 Query q = em.getEntityManager().createQuery("SELECT a FROM Album a");
	 List<Album> r = q.getResultList();
	 int expected = 207;
	 int actual = r.size();
	 assertEquals(expected, actual);
	}

	@Test 
	public void allAlbumsShort_ALL_207() {
		Query q = em.getEntityManager().createQuery("SELECT a.titreAlbum, s.titreSerie FROM Album a, Serie s WHERE a.serie = s ");
		List<Album> r = q.getResultList();
		int expected = 207;
		int actual = r.size();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAlbumsShort_Brigande_1() {
		Query q = em.getEntityManager().createQuery("SELECT NEW cfranc.com.comics.collector.ws.dto.AlbumShortDTO(a.titreAlbum,s.titreSerie) FROM Album a, Serie s WHERE a.serie = s AND a.titreAlbum= :titreAlbum");
		q.setParameter("titreAlbum", "Novgora");
		List<Album> r = q.getResultList();
		int expected = 1;
		int actual = r.size();
		assertEquals(expected, actual);
	}
	
	@Test 
	public void getCouple_Asterix_UderzoGoscinny() {
		Query q = em.getEntityManager().createQuery("SELECT NEW cfranc.com.comics.collector.ws.dto.AlbumShortDTO( (SELECT ap.personne.nomUsuel FROM Albumpersonne ap JOIN ap.album a2 WHERE a2 = a AND ap.metier.libelleMetier = 'Scenario'), (SELECT ap.personne.nomUsuel FROM Albumpersonne ap JOIN ap.album a2 WHERE a2 = a AND ap.metier.libelleMetier = 'Dessin'),	a.titreAlbum, s.titreSerie) FROM Album a LEFT JOIN a.serie s");
				
		List<Album> r = q.getResultList();
		int expected = 207;
		int actual = r.size();
		assertEquals(expected, actual);		
	}
	
	@Test 
	public void nbAlbums_All_39() {
		Query q = em.getEntityManager().createQuery("SELECT NEW cfranc.com.comics.collector.ws.dto.AlbumCountAuthorEditorDTO(count(a),a.editeur, ap.personne) FROM Album a LEFT JOIN a.albumpersonnes ap GROUP BY a.editeur.nomEditeur, ap.personne.nomUsuel ORDER BY count(a)");
		
		List<Album> r = q.getResultList();
		int expected = 39;
		int actual = r.size();
		assertEquals(expected, actual);
	}
}
