        // odd even
        Semaphore oddSemaPhore = new Semaphore(1);
        Semaphore evenSemaPhore = new Semaphore(0);
        Runnable printOdd = () -> {
            for(int i = 1 ; i < 100 ; i+=2){
                try{
                    oddSemaPhore.acquire();
                    System.out.println(i);
                    evenSemaPhore.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        };

        Runnable printEven = () -> {
            for(int i = 2 ; i < 100 ; i+=2 ){
                try{
                    evenSemaPhore.acquire();
                    System.out.println(i);
                    oddSemaPhore.release();
                    Thread.sleep(500);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        };
        Thread t1 = new Thread(printOdd);
        Thread t2 = new Thread(printEven);
        //t1.start();
        //t2.start();

        // producer-consumer

        Semaphore empty = new Semaphore(5);
        Semaphore full = new Semaphore(0);
        Semaphore lock = new Semaphore(1);
        Queue<Integer> buffer = new LinkedList<>();

        Runnable producer = () -> {
            int item = 1;
            while(true){
                try{
                    empty.acquire();
                    lock.acquire();

                    buffer.add(item);
                    System.out.println(item + "produced");
                    item++;
                    lock.release();
                    full.release();



                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        };
        Runnable consumer = () -> {
            while(true){
                try{
                   full.acquire();
                   lock.acquire();

                   int item = buffer.poll();
                   System.out.println(item + "consumed");
                    lock.release();
                   empty.release();

                   Thread.sleep(1000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
//        producerThread.start();
//        consumerThread.start();

        Semaphore printASema = new Semaphore(1);
        Semaphore printBSema = new Semaphore(0);
        Semaphore printCSema = new Semaphore(0);

        Runnable printA = () -> {
            try{
                while(true){
                    printASema.acquire();
                    System.out.println("A");
                    printBSema.release();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Runnable printB = () -> {
            try{
                while(true){
                    printBSema.acquire();
                    System.out.println("B");
                    printCSema.release();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };


        Runnable printC = () -> {
            try{

                while(true){
                    printCSema.acquire();
                    System.out.println("C");
                    printASema.release();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Thread threadForA  = new Thread(printA);
        Thread threadForB = new Thread(printB);
        Thread threadForC = new Thread(printC);

        threadForA.start();
        threadForB.start();
        threadForC.start();
