package com.example.springboot.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OperationMapper {

    @Select("select * from patient limit 1000")
    List<Map<String, Object>> getResult();

    @Insert("INSERT INTO patient (src_id, src_det_id, record_id, delete_flag, test_record_flag, insert_datetime, update_datetime, pcp_src_det_id, pcp_prov_id, record_id_qualifier, id_1, id_1_qualifier, id_2, id_2_qualifier, pref_record_flag, name_prefix, first_name, middle_name, last_name, name_suffix, full_name, birth_datetime, is_deceased_flag, deceased_datetime, ssn, gender, birth_gender, race, language, religion, ethnicity, military_status, pref_contact_method, activity_status_code, activity_status, email_address, home_phone, cell_phone, work_phone, home_address, home_address_addtl, home_city, home_state, home_zip, home_country, marital_status, pat_consent_txt, pat_type_code, pat_type_desc, pat_bad_debt_code, pat_status_code, pat_status_desc) VALUES (  #{src_id}, #{src_det_id}, #{record_id}, #{delete_flag}, #{test_record_flag}, #{insert_datetime}, #{update_datetime}, #{pcp_src_det_id}, #{pcp_prov_id}, #{record_id_qualifier}, #{id_1}, #{id_1_qualifier}, #{id_2}, #{id_2_qualifier}, #{pref_record_flag}, #{name_prefix}, #{first_name}, #{middle_name}, #{last_name}, #{name_suffix}, #{full_name}, #{birth_datetime}, #{is_deceased_flag}, #{deceased_datetime}, #{ssn}, #{gender}, #{birth_gender}, #{race}, #{language}, #{religion}, #{ethnicity}, #{military_status}, #{pref_contact_method}, #{activity_status_code}, #{activity_status}, #{email_address}, #{home_phone}, #{cell_phone}, #{work_phone}, #{home_address}, #{home_address_addtl}, #{home_city}, #{home_state}, #{home_zip}, #{home_country}, #{marital_status}, #{pat_consent_txt}, #{pat_type_code}, #{pat_type_desc}, #{pat_bad_debt_code}, #{pat_status_code}, #{pat_status_desc})")
    int insert(Map<String, Object> params);

    // src_id, pcp_src_det_id, pcp_prov_id
    @Select("select * from provider where src_id=#{src_id} and src_det_id=#{src_det_id} and record_id=#{record_id}")
    Map<String, Object> getProvider(Map<String, Object> params);

    @Insert("INSERT INTO provider (src_id, src_det_id, record_id, delete_flag, test_record_flag, insert_datetime, update_datetime, npi, name_prefix, first_name, middle_name, last_name, name_suffix, full_name, specialty, gender, birth_datetime, fax, phone, email_address, username, language, race, religion, ethnicity, prov_type, status) VALUES ( #{src_id}, #{src_det_id}, #{record_id}, #{delete_flag}, #{test_record_flag}, #{insert_datetime}, #{update_datetime}, #{npi}, #{name_prefix}, #{first_name}, #{middle_name}, #{last_name}, #{name_suffix}, #{full_name}, #{specialty}, #{gender}, #{birth_datetime}, #{fax}, #{phone}, #{email_address}, #{username}, #{language}, #{race}, #{religion}, #{ethnicity}, #{prov_type}, #{status})")
    int insertProvider(Map<String, Object> params);

    @Select("select src_det_id from source_detail where src_id=#{src_id} limit 2")
    List<String> getDetail(Map<String, Object> params);

    @Insert("INSERT INTO source_detail (src_id, src_det_id, src_detail_value, delete_flag, test_record_flag, insert_datetime, update_datetime) VALUES (#{src_id}, #{src_det_id}, #{src_detail_value}, #{delete_flag}, #{test_record_flag}, #{insert_datetime}, #{update_datetime});")
    int insertSourceDetail(Map<String, Object> params);

    @Insert("INSERT IGNORE INTO ywt_eac_inf_b (uid, card_no, clt_cid, cd_flg, ywt_name) VALUES (#{uid}, #{no}, #{cid}, '1', #{ywt})")
    int insertIntoYwt(Map<String, Object> params);

}
