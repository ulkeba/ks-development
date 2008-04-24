package org.kuali.student.poc.learningunit.lu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.CluSet;
import org.kuali.student.poc.learningunit.lu.entity.LuAttribute;
import org.kuali.student.poc.learningunit.lu.entity.LuAttributeType;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.poc.learningunit.lu.dao.impl.LuDaoImpl", testDataFile = "classpath:test-beans.xml")
	public LuDao dao;

	// Beans (defined and preloaded in test-beans.xml)
	public LuType luType1;
	public Atp atpStart;
	public Atp atpEnd;
	public Atp atp1;
	public LuAttributeType luAttrTyp1;
	public static final String clu1_id = "11223344-1122-1122-11-11-000000000005";
	public Clu clu2;
	public Lui lui1;
	public Lui lui2;
	public LuRelationType luRelationType1;
	public CluRelation cluRelation1;
	public CluSet cluSet2;
	public LuiRelation luiRelation1;

	@Test
	public void testCreateClu() {

		Clu clu = new Clu();
		LuAttribute at1 = new LuAttribute();
		at1.setClu(clu);
		at1.setLuAttributeType(luAttrTyp1);
		at1.setValue("Attr VALUE 1");
		clu.setEffectiveStartCycle(atpStart);
		clu.setEffectiveEndCycle(atpEnd);
		clu.getAttributes().add(at1);
		clu.setApprovalStatus("Approved");
		clu.setApprovalStatusTime("ShouldBeDate?");
		clu.setCluLongName("Clu LongName 2345");
		clu.setCluShortName("Clu ShortName 2345");
		clu.setDescription("CREATED CLU DESCRIPTION LA LA LA");
		clu.setEffectiveEndDate(new Date());
		clu.setEffectiveStartDate(new Date());
		clu.setCreateTime(new Date());
		clu.setCreateUserComment("CreateUserComment");
		clu.setCreateUserId("CreateUserId");
		clu.setUpdateTime(new Date());
		clu.setUpdateUserComment("UpdateUserComment");
		clu.setUpdateUserId("UpdateUserId");
		clu.setLuType(luType1);
		dao.createClu(clu);
		assertEquals(em.find(Clu.class, clu.getCluId()), clu);
	}

	@Test
	public void testCreateAtp() {
		Atp atp = new Atp();
		atp.setAtpName("CREATED Atp");
		dao.createAtp(atp);
		assertEquals(em.find(Atp.class, atp.getAtpId()), atp);
	}

	@Test
	public void testCreateLuType() {
		LuType luType = new LuType();
		luType.setCreateTime(new Date());
		luType.setCreateUserComment("Created");
		luType.setCreateUserId("USER-12345");
		luType.setUpdateTime(new Date());
		luType.setUpdateUserComment("Created");
		luType.setUpdateUserId("USER-12345");
		luType.getLuAttributeTypes().add(luAttrTyp1);
		dao.createLuType(luType);
		assertEquals(em.find(LuType.class, luType.getLuTypeId()), luType);
	}

	@Test
	public void testCreateLui() {
		Lui lui = new Lui();
		lui.setAtp(atp1);
		lui.setClu(em.find(Clu.class, clu1_id));
		lui.setLuiCode("CREATED Lui Code");
		lui.setMaxSeats(128);
		dao.createLui(lui);
		assertEquals(em.find(Lui.class, lui.getLuiId()), lui);
	}

	@Test
	public void testCreateLuiRelation() {
		LuiRelation luiRelation = new LuiRelation();
		luiRelation.setCreateTime(new Date());
		luiRelation.setCreateUserComment("CreateUserComment");
		luiRelation.setCreateUserId("CreateUserId");
		luiRelation.setEffectiveEndDate(new Date());
		luiRelation.setEffectiveStartDate(new Date());
		luiRelation.setLui(lui1);
		luiRelation.setLuRelationType(luRelationType1);
		luiRelation.setRelatedLui(lui2);
		luiRelation.setUpdateTime(new Date());
		luiRelation.setUpdateUserComment("UpdateUserComment");
		luiRelation.setUpdateUserId("UpdateUserId");
		dao.createLuiRelation(luiRelation);
		assertEquals(em.find(LuiRelation.class, luiRelation.getId()),
				luiRelation);
	}

	@Test
	public void testCreateLuRelationType() {
		LuRelationType luRelationType = new LuRelationType();
		luRelationType.setRelation("Relation Type One");
		dao.createLuRelationType(luRelationType);
		assertEquals(em.find(LuRelationType.class, luRelationType.getId()),
				luRelationType);
	}

	@Test
	public void testCreateCluRelation() {
		CluRelation cluRelation = new CluRelation();
		cluRelation.setCreateTime(new Date());
		cluRelation.setCreateUserComment("CreateUserComment");
		cluRelation.setCreateUserId("CreateUserId");
		cluRelation.setEffectiveEndDate(new Date());
		cluRelation.setEffectiveStartDate(new Date());
		cluRelation.setClu(em.find(Clu.class, clu1_id));
		cluRelation.setLuRelationType(luRelationType1);
		cluRelation.setRelatedClu(clu2);
		cluRelation.setUpdateTime(new Date());
		cluRelation.setUpdateUserComment("UpdateUserComment");
		cluRelation.setUpdateUserId("UpdateUserId");
		dao.createCluRelation(cluRelation);
		assertEquals(em.find(CluRelation.class, cluRelation.getId()),
				cluRelation);
	}

	@Test
	public void testCreateCluSet() {
		CluSet cluSet1 = new CluSet();
		cluSet1.setCluCriteria("CLU*LA%");
		cluSet1.setCluSetName("Set Number 1");
		cluSet1.setDescription("Set1 Description");
		cluSet1.setEffectiveEndDate(new Date());
		cluSet1.setEffectiveStartDate(new Date());
		cluSet1.getCluList().add(em.find(Clu.class, clu1_id));
		dao.createCluSet(cluSet1);

		CluSet cluSet2 = new CluSet();
		cluSet2.setCluCriteria("CLU*LA%");
		cluSet2.setCluSetName("Set Number 1");
		cluSet2.setDescription("Set1 Description");
		cluSet2.setEffectiveEndDate(new Date());
		cluSet2.setEffectiveStartDate(new Date());
		cluSet2.getCluList().add(clu2);
		cluSet2.getCluSetList().add(cluSet1);
		dao.createCluSet(cluSet2);

		assertEquals(em.find(CluSet.class, cluSet2.getCluSetId()), cluSet2);
		assertEquals(em.find(CluSet.class, cluSet2.getCluSetId())
				.getCluSetList().get(0), cluSet1);
	}

	public void testDeleteClu() {
		String cluId = clu1_id;
		dao.deleteClu(em.find(Clu.class, clu1_id));
		assertNull(em.find(Clu.class, cluId));
	}

	public void testDeleteCluRelation() {
		String id = cluRelation1.getId();
		dao.deleteCluRelation(cluRelation1);
		assertNull(em.find(CluRelation.class, id));
	}

	public void testDeleteCluSet() {
		String id = cluSet2.getCluSetId();
		dao.deleteCluSet(cluSet2);
		assertNull(em.find(CluSet.class, id));
	}

	public void testDeleteLui() {
		String id = lui1.getLuiId();
		dao.deleteLui(lui1);
		assertNull(em.find(Lui.class, id));
	}

	public void testDeleteLuiRelation() {
		String id = luiRelation1.getId();
		dao.deleteLuiRelation(luiRelation1);
		assertNull(em.find(CluRelation.class, id));
	}

	public void testUpdateClu() {
		Date currentTime = new Date();
		Clu clu1 = em.find(Clu.class, clu1_id);
		clu1.setEffectiveStartCycle(atpEnd);
		clu1.setEffectiveEndCycle(atpStart);
		clu1.getAttributes().iterator().next().setValue(
				"UPDATED ATTRIBUTE VALUE");
		clu1.setCluLongName("UPDATED Clu code 2345");
		clu1.setCluShortName("UPDATED Clu ShortTitle");
		clu1.setDescription("UPDATED CLU DESCRIPTION LA LA LA");
		clu1.setEffectiveStartDate(currentTime);
		clu1.setEffectiveEndDate(currentTime);
		dao.updateClu(clu1);
		Clu updated = em.find(Clu.class, clu1.getCluId());
		assertEquals(atpEnd, updated.getEffectiveStartCycle());
		assertEquals(atpStart, updated.getEffectiveEndCycle());
		assertEquals("UPDATED ATTRIBUTE VALUE", updated.getAttributes()
				.iterator().next().getValue());

	}

	public void testUpdateCluRelation() {

	}

	public void testUpdateCluSet() {

	}

	public void testUpdateLui() {

	}

	public void testUpdateLuiRelation() {

	}

}
