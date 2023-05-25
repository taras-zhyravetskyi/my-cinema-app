package cinema.config;

import cinema.dao.RoleDao;
import cinema.model.Role;
import cinema.model.User;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final RoleDao roleDao;

    public DataInitializer(RoleService roleService, UserService userService, RoleDao roleDao) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @PostConstruct
    public void inject() {
        if (roleDao.getByName(Role.RoleName.ADMIN.name()).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName(Role.RoleName.ADMIN);
            roleService.add(adminRole);
            if (userService.findByEmail("admin@i.ua").isEmpty()) {
                User user = new User();
                user.setEmail("admin@i.ua");
                user.setPassword("admin123");
                user.setRoles(Set.of(adminRole));
                userService.add(user);
            }
        }
        if (roleDao.getByName(Role.RoleName.USER.name()).isEmpty()) {
            Role userRole = new Role();
            userRole.setRoleName(Role.RoleName.USER);
            roleService.add(userRole);
        }
    }
}
