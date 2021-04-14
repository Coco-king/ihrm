package top.codecrab.system.base;

import org.springframework.beans.factory.annotation.Autowired;
import top.codecrab.common.utils.IdWorker;
import top.codecrab.system.repository.PermissionRepository;
import top.codecrab.system.repository.RoleRepository;
import top.codecrab.system.repository.UserRepository;

/**
 * @author codecrab
 * @since 2021年04月09日 11:16
 */
public class BaseService {

    protected static final String NULL = "null";
    protected static final String ZERO = "0";

    @Autowired
    protected IdWorker idWorker;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PermissionRepository permissionRepository;
}
