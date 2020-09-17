package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

//        List<User> myList = userService.findAll();
//        for (User u : myList) {
//            System.out.println(u.getUserid() + " " + u.getUsername());
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById() {
        assertEquals("cinnamon", userService.findUserById(7).getUsername());
    }
    @Test (expected = ResourceNotFoundException.class)
    public void aa_findUserByIdNotFound() {
        assertEquals("cinnamon", userService.findUserById(1000).getUsername());
    }


    @Test
    public void b_findByNameContaining() {
        assertEquals(1, userService.findByNameContaining("miss").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_findByName() {
        assertEquals("barnbarn", userService.findByName("barnbarn").getUsername());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void dd_findByNameFailed() {
        assertEquals("Lambda", userService.findByName("Stumps").getUsername());
    }

    @Test
    public void t_save() {
        User u5 = new User("misspiggy",
                "password",
                "misspiggy@school.lambda");
        u5.getRoles()
                .add(new UserRoles(u5, roleService.findAll().get(1)));
        u5.getUseremails()
                .add(new Useremail(u5, "sesamestreet@email.com"));

        User addUser = userService.save(u5);
        assertNotNull(addUser);
        assertEquals("misspiggy", addUser.getUsername());
    }

    @Test
    public void tt_saveput() {
        User u5 = new User("bigbird",
                "password",
                "big@school.lambda");
        u5.getRoles()
                .add(new UserRoles(u5, roleService.findAll().get(1)));
        u5.getUseremails()
                .add(new Useremail(u5, "sesamestreet@email.com"));

        User addUser = userService.save(u5);
        assertNotNull(addUser);
        assertEquals("bigbird", addUser.getUsername());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void ttt_saveputfailed() {
        User u5 = new User("bigbird",
                "password",
                "big@school.lambda");
        u5.setUserid(777);
        u5.getRoles()
                .add(new UserRoles(u5, roleService.findAll().get(1)));
        u5.getUseremails()
                .add(new Useremail(u5, "sesamestreet@email.com"));

        User addUser = userService.save(u5);
        assertNotNull(addUser);
        assertEquals("bigbird", addUser.getUsername());
    }

    @Test
    public void f_delete() {
        userService.delete(13);
        assertEquals(4, userService.findAll().size());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void ff_deleteFailed() {
        userService.delete(777);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_update() {
        User u4 = new User("misspiggy",
                "password",
                "misspiggy@school.lambda");
        u4.setUserid(13);
        u4.getRoles()
                .add(new UserRoles(u4, roleService.findAll().get(1)));
        u4.getUseremails()
                .add(new Useremail(u4, "sesamestreet@email.com"));
        u4.getUseremails()
                .add(new Useremail(u4, "ilovepiggy@email.com"));

        User addUser = userService.update(u4, 13);
        assertNotNull(addUser);
        assertEquals("misspiggy", addUser.getUsername());
    }

    @Test
    public void g_deleteAll() {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}