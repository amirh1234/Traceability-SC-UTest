    public void testFindIsolateResources() throws Exception {
        String buildTestcases = System.getProperty("build.tests");
        assertNotNull("defined ${build.tests}", buildTestcases);
        assertTrue("have a dir " + buildTestcases,
                   new File(buildTestcases).isDirectory());
        Path path = new Path(p, buildTestcases + "/org");
        ClassLoader parent = new ParentLoader();

        URL urlFromPath = new URL(
            FILE_UTILS.toURI(buildTestcases) + "org/" + TEST_RESOURCE);
        AntClassLoader acl = new AntClassLoader(parent, p, path, false);
        acl.setIsolated(true);
        assertEquals("correct resources (reverse delegation order)",
        		enum2List(acl.getResources(TEST_RESOURCE)),
            enum2List(acl.getResources(TEST_RESOURCE)));
    }