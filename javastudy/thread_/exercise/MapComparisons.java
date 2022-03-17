package javastudy.thread_.exercise;
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe Map performance.

import javastudy.utilities.RandomGenerator;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

abstract class MapTest extends Tester<Map<Integer, Integer>> {
	MapTest(String testId, int nReaders, int nWriters) {
		super(testId, nReaders, nWriters);
	}

	class Reader extends TestTask {
		long result = 0;

		void test() {
			for (long i = 0; i < testCycles; i++)
				for (int index = 0; index < containerSize; index++)
					result += testContainer.get(index);
		}

		void putResults() {
			readResult += result;
			readTime += duration;
		}
	}

	class Writer extends TestTask {
		void test() {
			for (long i = 0; i < testCycles; i++)
				for (int index = 0; index < containerSize; index++)
					testContainer.put(index, writeData[index]);
		}

		void putResults() {
			writeTime += duration;
		}
	}

	void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++)
			exec.execute(new Reader());
		for (int i = 0; i < nWriters; i++)
			exec.execute(new Writer());
	}
}

class SynchronizedHashMapTest extends MapTest {
	Map<Integer, Integer> containerInitializer() {
		return Collections.synchronizedMap(RandomGenerator.Collection.getRandomHashMap(containerSize));
	}

	SynchronizedHashMapTest(int nReaders, int nWriters) {
		super("Synched HashMap", nReaders, nWriters);
	}
}

class ConcurrentHashMapTest extends MapTest {
	Map<Integer, Integer> containerInitializer() {
		return new ConcurrentHashMap<>(RandomGenerator.Collection.getRandomHashMap(containerSize));
	}

	ConcurrentHashMapTest(int nReaders, int nWriters) {
		super("ConcurrentHashMap", nReaders, nWriters);
	}
}

// 因为多了加锁和释放锁的操作，所以不能继承MapTest来测试
class ReaderWriterMapTest extends Tester<Map<Integer, Integer>> {
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	ReaderWriterMapTest(int nReaders, int nWriters) {
		super("ReaderWriterMap", nReaders, nWriters);
	}

	class Reader extends TestTask {
		long result = 0;

		void test() {
			ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
			readLock.lock();
			try {
				for (long i = 0; i < testCycles; i++)
					for (int index = 0; index < containerSize; index++)
						result += testContainer.get(index);
			} finally {
				readLock.unlock();
			}
		}

		void putResults() {
			readResult += result;
			readTime += duration;
		}
	}

	class Writer extends TestTask {
		void test() {
			ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();
			writeLock.lock();
			try {
				for (long i = 0; i < testCycles; i++)
					for (int index = 0; index < containerSize; index++)
						testContainer.put(index, writeData[index]);
			} finally {
				writeLock.unlock();
			}
		}

		void putResults() {
			writeTime += duration;
		}
	}

	@Override
	Map<Integer, Integer> containerInitializer() {
		return RandomGenerator.Collection.getRandomHashMap(containerSize);
	}

	void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++)
			exec.execute(new Reader());
		for (int i = 0; i < nWriters; i++)
			exec.execute(new Writer());
	}
}

public class MapComparisons {
	public static void main(String[] args) {
		Tester.initMain(args);
		new SynchronizedHashMapTest(10, 0);
		// new SynchronizedHashMapTest(9, 1);
		// new SynchronizedHashMapTest(5, 5);
		new ConcurrentHashMapTest(10, 0);
		// new ConcurrentHashMapTest(9, 1);
		// new ConcurrentHashMapTest(5, 5);
		new ReaderWriterMapTest(10, 0);
		// new ReaderWriterMapTest(5, 5);
		Tester.exec.shutdown();
		// ConcurrentHashMapTest全能战士，有大量读操作的话ReaderWriterMap更胜一筹，但是写入越多越不利
	}
}
