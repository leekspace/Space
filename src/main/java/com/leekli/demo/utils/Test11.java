package com.leekli.demo.utils;

public class Test11 {

	//最大的块大小
	private static final int MAX_CHUNK_SIZE = (int) (((long) Integer.MAX_VALUE + 1) / 2);
	
	public static void main(String[] args) {
		int pageSize = 4096 * 2;
		System.out.println(pageSize & pageSize - 1);
		
		
		
		int chunkSize = pageSize;
        int maxOrder = 11;
		for (int i = maxOrder ; i > 0; i --) {
            if (chunkSize > MAX_CHUNK_SIZE / 2) {
                throw new IllegalArgumentException(String.format(
                        "pageSize (%d) << maxOrder (%d) must not exceed %d", pageSize, maxOrder, MAX_CHUNK_SIZE));
            }
            chunkSize <<= 1;
        }
		System.out.println("MAX_CHUNK_SIZE:"+MAX_CHUNK_SIZE);
		System.out.println(chunkSize);
	}
}

