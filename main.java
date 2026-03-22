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

               Queue<Integer> q = new LinkedList<>();
        Semaphore cSema = new Semaphore(0);
        Semaphore pSema = new Semaphore(5);

        Runnable producer = () -> {
            int item = 0;
            while(true){

                try{
                    pSema.acquire();
                    item++;
                    q.add(item);
                    System.out.println("Produced" + " " + item);
                    cSema.release();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable consumer = () -> {
            while(true){

                try{
                    cSema.acquire();
                    int item = q.remove();
                    System.out.println("Consumed" + " " + item);
                    pSema.release();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();



        // print abc
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
