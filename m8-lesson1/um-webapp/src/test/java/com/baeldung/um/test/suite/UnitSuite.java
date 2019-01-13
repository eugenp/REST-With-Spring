package com.baeldung.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.um.service.impl.UserServiceUnitTest;
import com.baeldung.um.service.impl.PrivilegeServiceUnitTest;
import com.baeldung.um.service.impl.RoleServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({ UserServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class })
public final class UnitSuite {
    //
}
