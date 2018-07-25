public void test7() {
        executeTarget("test7");
        assertEquals("original", Project.getProperty("test"));
    }