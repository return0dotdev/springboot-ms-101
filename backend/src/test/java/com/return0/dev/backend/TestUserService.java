package com.return0.dev.backend;

import com.return0.dev.backend.entity.Address;
import com.return0.dev.backend.entity.Social;
import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.UserException;
import com.return0.dev.backend.service.AddressService;
import com.return0.dev.backend.service.SocialService;
import com.return0.dev.backend.service.UserService;
import com.return0.dev.backend.utils.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Order(1)
    @Test
    @DisplayName("TestCreate test successful")
    void testCreate() throws BaseException {
        User user = userService.create(TestCreateData.email, TestCreateData.password, TestCreateData.name, SecurityUtil.generateToken());

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        Assertions.assertEquals(TestCreateData.name, user.getName());
        Assertions.assertEquals(TestCreateData.email, user.getEmail());

        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
        Assertions.assertTrue(isMatched);
    }

    @Order(2)
    @Test
    void testUpdate() throws UserException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User updatedUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(TestUpdateData.name, updatedUser.getName());
    }



    @Order(3)
    @Test
//    @Disabled
    void testCreateSocial() throws UserException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.create(user,
                SocialTestCreateData.facebook,
                SocialTestCreateData.line,
                SocialTestCreateData.instagram,
                SocialTestCreateData.tiktok);

        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());
    }

    @Order(4)
    @Test
//    @Disabled
    void testCreateAddress() throws UserException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());

        createAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        createAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);

    }

    private void  createAddress(User user, String line1, String line2, String zipcode){
        Address address = addressService.create(user,
                line1,
                line2,
                zipcode);

        Assertions.assertNotNull(address);
        Assertions.assertEquals(line1, address.getLine1());
        Assertions.assertEquals(line2, address.getLine2());
        Assertions.assertEquals(zipcode, address.getZipcode());
    }

    @Order(9)
    @Test
//    @Disabled
    void testDelete() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();


        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());

        List<Address> addresses = user.getAddresses();
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertEquals(2, addresses.size());

        userService.deleteById(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(optDelete.isEmpty());
    }

    interface TestCreateData {
        String email = "phoommin.n7@gmaul.com";
        String password = "1234EE";
        String name = "phoommin.naksenee";
    }

    interface SocialTestCreateData {
        String facebook = "return0.facebook";
        String line = "return0.line";
        String instagram = "return0.instagram";
        String tiktok = "return0.tiktok";
    }

    interface AddressTestCreateData {
        String line1 = "return0.line1";
        String line2 = "return0.line2";
        String zipcode = "20020";
    }

    interface AddressTestCreateData2 {
        String line1 = "return0-1.line1";
        String line2 = "return0-1.line2";
        String zipcode = "20021";
    }

    interface TestUpdateData {
        String email = "phoommin.n6@gmaul.com";
        String password = "1234EE";
        String name = "phoommin.nakseneeZaa";
    }
}
