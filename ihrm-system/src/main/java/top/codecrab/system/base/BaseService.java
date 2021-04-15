package top.codecrab.system.base;

import org.springframework.beans.factory.annotation.Autowired;
import top.codecrab.common.utils.IdWorker;
import top.codecrab.system.repository.*;

/**
 * @author codecrab
 * @since 2021年04月09日 11:16
 */
public class BaseService {
    @Autowired
    protected IdWorker idWorker;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PermissionRepository permissionRepository;
    @Autowired
    protected PermissionMenuRepository permissionMenuRepository;
    @Autowired
    protected PermissionPointRepository permissionPointRepository;
    @Autowired
    protected PermissionApiRepository permissionApiRepository;
}
