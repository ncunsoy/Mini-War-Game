import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class EndWindow extends JFrame{
    String message="  ";
    public EndWindow(String message){
        this.message=message;
        setSize(200,200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Text text=new Text();
        add(text);

    }

    class Text extends JLabel{
        public Text(){
            setSize(150,150);
            setVisible(true);
            setText(message);
        }
    }
}

public class Game extends JFrame implements KeyListener, MouseInputListener {
    ArrayList <Enemy> enemies=new ArrayList<>();
    ArrayList <Friend> friends=new ArrayList<>();
    ArrayList <EnemyLeftBullets> enemyLeftBullets=new ArrayList<>();
    ArrayList <EnemyRightBullets> enemyRightBullets=new ArrayList<>();
    ArrayList <FriendLeftBullets> friendLeftBullets=new ArrayList<>();
    ArrayList <FriendRightBullets> friendRightBullets=new ArrayList<>();
    ArrayList <AirCraftRightBullet>  craftRightBullet=new ArrayList<>();
    ArrayList <AirCraftLeftBullet> craftLeftBullet=new ArrayList<>();
    ReentrantLock lock=new ReentrantLock();
    boolean isContinue=true;
    String message="";

    public Game(){
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);


    }

    public void paint(Graphics g) {
        lock.lock();
        if (isContinue) {
            super.paint(g);

            g.setColor(Color.RED);
            g.fillRect(AirCraft.x, AirCraft.y, 10, 10);

            g.setColor(Color.BLACK);
            for (int i = 0; i < enemies.size(); i++) {
                g.fillRect(enemies.get(i).x, enemies.get(i).y, 10, 10);
            }

            g.setColor(Color.GREEN);
            for (int i = 0; i < friends.size(); i++) {
                g.fillRect(friends.get(i).x, friends.get(i).y, 10, 10);
            }

            g.setColor(Color.BLUE);
            for (int i = 0; i < enemyRightBullets.size(); i++) {
                g.fillRect(enemyRightBullets.get(i).x, enemyRightBullets.get(i).y, 5, 5);
            }

            for (int i = 0; i < enemyLeftBullets.size(); i++) {
                g.fillRect(enemyLeftBullets.get(i).x, enemyLeftBullets.get(i).y, 5, 5);
            }

            g.setColor(Color.MAGENTA);
            for (int i = 0; i < friendRightBullets.size(); i++) {
                g.fillRect(friendRightBullets.get(i).x, friendRightBullets.get(i).y, 5, 5);
            }

            for (int i = 0; i < friendLeftBullets.size(); i++) {
                g.fillRect(friendLeftBullets.get(i).x, friendLeftBullets.get(i).y, 5, 5);
            }

            g.setColor(Color.ORANGE);
            for (int i = 0; i < craftRightBullet.size(); i++) {
                g.fillRect(craftRightBullet.get(i).x, craftRightBullet.get(i).y, 5, 5);
            }

            for (int i = 0; i < craftLeftBullet.size(); i++) {
                g.fillRect(craftLeftBullet.get(i).x, craftLeftBullet.get(i).y, 5, 5);
            }
        } else {
            if(isActive()){
                dispose();
                EndWindow endWindow = new EndWindow(message);
            }
        }

        lock.unlock();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()=='a'){
            if(AirCraft.x>15)
                AirCraft.x=AirCraft.x-10;
        }

        if(e.getKeyChar()=='d'){
            if(AirCraft.x<480)
                AirCraft.x=AirCraft.x+10;
        }

        if(e.getKeyChar()=='s'){
            if(AirCraft.y<480)
                AirCraft.y=AirCraft.y+10;
        }

        if(e.getKeyChar()=='w'){
            if(AirCraft.y>35)
                AirCraft.y=AirCraft.y-10;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='a'){
            if(AirCraft.x>15)
                AirCraft.x=AirCraft.x-10;
        }

        if(e.getKeyChar()=='d'){
            if(AirCraft.x<480)
                AirCraft.x=AirCraft.x+10;
        }

        if(e.getKeyChar()=='s'){
            if(AirCraft.y<480)
                AirCraft.y=AirCraft.y+10;
        }

        if(e.getKeyChar()=='w'){
            if(AirCraft.y>35)
                AirCraft.y=AirCraft.y-10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AirCraftLeftBullet l=new AirCraftLeftBullet(AirCraft.x-10,AirCraft.y);
        AirCraftRightBullet r=new AirCraftRightBullet(AirCraft.x+10,AirCraft.y);
        l.start();
        r.start();
        try {
            l.join(500);
            r.join(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }



    class AirCraft extends Thread{
        static int x=250;
        static int y=250;

        public AirCraft (){

        }

        @Override
        public void run() {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            for(int i=0;i<enemies.size();i++){
                if(x==enemies.get(i).x && y==enemies.get(i).y){
                    message = "Oyunu kaybettiniz.";
                    isContinue=false;
                }
            }
            lock.unlock();
            paint(getGraphics());
        }
    }



    class Enemy extends Thread{
        int x=0;
        int y=0;
        boolean exit=false;


        public Enemy(){
            Random random=new Random();
            x=random.nextInt(2,49)*10;
            y=random.nextInt(2,49)*10;

            for(int i=0;i<enemies.size();i++){
                if(x==AirCraft.x && y ==AirCraft.y){
                    x=random.nextInt(2,49)*10;
                    y=random.nextInt(2,49)*10;
                    i=0;
                }

                if(x==enemies.get(i).x && y==enemies.get(i).y){
                    x=random.nextInt(2,49)*10;
                    y=random.nextInt(2,49)*10;
                    i=0;
                }
                for(int j=0;j<friends.size();j++){
                    if(x==friends.get(j).x && y==friends.get(j).y){
                        x=random.nextInt(2,49)*10;
                        y=random.nextInt(2,49)*10;
                        j=0;
                        i=0;
                    }
                }
            }
            enemies.add(this);
        }

        public void run(){
            while (!exit && isContinue){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Random random=new Random();
                while (true){
                    int direction=random.nextInt(1,5);
                    if(direction==1){
                        if(y>35){
                            y-=10;
                            if(canMove())
                                break;
                            y+=10;
                        }
                    }
                    if(direction==2){
                        if(y<480){
                            y+=10;
                            if(canMove())
                                break;
                            y-=10;
                        }
                    }
                    if(direction==3){
                        if(x>15){
                            x-=10;
                            if(canMove())
                                break;
                            x+=10;
                        }
                    }
                    if(direction==4){
                        if(x<480){
                            x+=10;
                            if(canMove())
                                break;
                            x-=10;
                        }
                    }
                }

                lock.lock();
                EnemyLeftBullets l=new EnemyLeftBullets(x-10,y);
                EnemyRightBullets r=new EnemyRightBullets(x+10,y);
                lock.unlock();
                l.start();
                r.start();
                try {
                    l.join(500);
                    r.join(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                crush();
                paint(getGraphics());

            }

        }
        public boolean canMove(){
            for(int i=0;i<enemies.size();i++){
                if(enemies.get(i).equals(this))
                    continue;
                if(x==enemies.get(i).x && y==enemies.get(i).y)
                    return false;
            }

            return true;
        }

        public void crush(){
            /*if(x==AirCraft.x+10 && y==AirCraft.y){
                message="Oyunu kaybettiniz.";
                isContinue=false;
            }


            else if(x==AirCraft.x-10 && y==AirCraft.y) {
                message="Oyunu kaybettiniz.";
                isContinue = false;
            }

            else if(x==AirCraft.x && y==AirCraft.y+10) {
                message = "Oyunu kaybettiniz.";
                isContinue = false;
            }

            else if(x==AirCraft.x && y==AirCraft.y-10) {
                message = "Oyunu kaybettiniz.";
                isContinue = false;
            }*/

            if(x==AirCraft.x && y==AirCraft.y) {
                message = "Oyunu kaybettiniz.";
                isContinue = false;
            }

            if(!isContinue){
                System.out.println("Bitti");
            }


        }
    }




    class Friend extends Thread{
        int x=0;
        int y=0;
        boolean exit=false;


        public Friend(){
            Random random=new Random();
            x=random.nextInt(2,49)*10;
            y=random.nextInt(2,49)*10;

            for(int i=0;i<enemies.size();i++){
                if(x==AirCraft.x && y ==AirCraft.y){
                    x=random.nextInt(2,49)*10;
                    y=random.nextInt(2,49)*10;
                    i=0;
                }
                if(x==enemies.get(i).x && y==enemies.get(i).y){
                    x=random.nextInt(2,49)*10;
                    y=random.nextInt(2,49)*10;
                    i=0;
                }

                for(int j = 0; j <friends.size(); j++){
                    if(x==friends.get(j).x && y==friends.get(j).y){
                        x=random.nextInt(2,49)*10;
                        y=random.nextInt(2,49)*10;
                        j =0;
                        i=0;
                    }
                }
            }
            friends.add(this);
        }

        public void run(){
            while (!exit && isContinue){
                try {
                     Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Random random=new Random();
                while (true){
                    int direction=random.nextInt(1,5);
                    if(direction==1){
                        if(y>35){
                            y-=10;
                            if(canMove())
                                break;
                            y+=10;
                        }
                    }
                    if(direction==2){
                        if(y<480){
                            y+=10;
                            if(canMove())
                                break;
                            y-=10;
                        }
                    }
                    if(direction==3){
                        if(x>10){
                            x-=15;
                            if(canMove())
                                break;
                            x+=10;
                        }
                    }
                    if(direction==4){
                        if(x<480){
                            x+=10;
                            if(canMove())
                                break;
                            x-=10;
                        }
                    }
                }

                delete();
                paint(getGraphics());
                lock.lock();
                FriendLeftBullets  l=new FriendLeftBullets(x-10,y);
                FriendRightBullets  r=new FriendRightBullets(x+10,y);
                lock.unlock();

                l.start();
                r.start();
                try {
                    l.join(500);
                    r.join(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public boolean canMove(){
            for(int i=0;i<friends.size();i++){
                if(friends.get(i).equals(this))
                    continue;
                if(x==friends.get(i).x && y==friends.get(i).y)
                    return false;
                if(x==AirCraft.x && y==AirCraft.y)
                    return false;
            }

            return true;
        }

        public void delete(){

            for(int i=0;i<enemies.size();i++){
                if(y==enemies.get(i).y && x==enemies.get(i).x){
                    enemies.get(i).exit=true;
                    this.exit=true;
                    lock.lock();
                    enemies.remove(i);
                    friends.remove(this);
                    if(enemies.size()==0){
                        message="Oyunu kazandınız";
                        isContinue=false;
                    }
                    lock.unlock();
                    break;

                }
                /*if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    enemies.get(i).exit=true;
                    this.exit=true;
                    lock.lock();
                    enemies.remove(i);
                    friends.remove(this);
                    if(enemies.size()==0){
                        message="Oyunu kazandınız";
                        isContinue=false;
                    }
                    lock.unlock();
                    break;

                }*/


                /*if(x<=enemies.get(i).x+10 && y==enemies.get(i).y){
                (x<enemies.get(i).x+10 && x>enemies.get(i).x-10 && y<enemies.get(i).y+10 && y>enemies.get(i).y-10)
                (y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10)
                    enemies.remove(i);
                    friends.remove(this);
                }
                else if(x==enemies.get(i).x-10 && y==enemies.get(i).y){
                    enemies.remove(i);
                    friends.remove(this);
                }
                else if(x==enemies.get(i).x && y==enemies.get(i).y+10){
                    enemies.remove(i);
                    friends.remove(this);
                }
                else if(x==enemies.get(i).x && y==enemies.get(i).y-10){
                    enemies.remove(i);
                    friends.remove(this);
                }
                else if(x==enemies.get(i).x && y==enemies.get(i).y){
                    enemies.remove(i);
                    friends.remove(this);
                }*/

            }
        }
    }

    class EnemyRightBullets extends Thread{
        int x=0;
        int y=0;
        boolean die=false;

        public EnemyRightBullets(int x,int y){
            this.x=x+10;
            this.y=y;
            enemyRightBullets.add(this);
        }
        public void run(){

            while (!die && isContinue){
                if(x<500){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.x+=10;
                    paint(getGraphics());
                    if(lose()){
                        continue;
                    }
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }
                else {
                    lock.lock();
                    enemyRightBullets.remove(this);
                    die=true;
                    lock.unlock();
                }

            }

        }
        public boolean lose(){
            if(y>=AirCraft.y && y<AirCraft.y+10 && x>=AirCraft.x && x<=AirCraft.x+10){
                message="Oyunu Kaybettiniz.";
                isContinue=false;
                return true;
            }
            return false;
        }
        public void dissappear(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    lock.lock();
                    die=true;
                    enemyRightBullets.remove(this);
                    lock.unlock();
                }
            }
        }

        public void defeat(){

            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<=friends.get(i).y+10 && x>=friends.get(i).x && x<=friends.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    lock.lock();
                    enemyRightBullets.remove(this);
                    if(i<friends.size()) {
                        friends.get(i).exit = true;
                        friends.remove(i);
                    }
                    lock.unlock();
                }
            }
        }
    }

    class EnemyLeftBullets extends Thread{
        int x=0;
        int y=0;
        boolean die=false;

        public EnemyLeftBullets(int x,int y){
            this.x=x;
            this.y=y;
            enemyLeftBullets.add(this);

        }
        public void run(){
            while (!die && isContinue){
                if(x>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.x-=10;
                    paint(getGraphics());
                    if(lose()){
                        continue;
                    }
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }
                else {
                    lock.lock();
                    enemyLeftBullets.remove(this);
                    die=true;
                    lock.unlock();
                }

            }
        }
        public void dissappear(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    lock.lock();
                    die=true;
                    enemyLeftBullets.remove(this);
                    lock.unlock();
                }
            }
        }

        public void defeat(){
            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<friends.get(i).y+10 && x>=friends.get(i).x && x<friends.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    lock.lock();
                    enemyLeftBullets.remove(this);
                    if(i<friends.size()) {
                        friends.get(i).exit = true;
                        friends.remove(i);
                    }
                    lock.unlock();
                }
            }
        }

        public boolean lose(){
            if(y>=AirCraft.y && y<AirCraft.y+10 && x>=AirCraft.x && x<AirCraft.x+10){
                message="Oyunu Kaybettiniz.";
                isContinue=false;
                return true;
            }
            return false;
        }
    }

    class FriendRightBullets extends Thread{
        int x=0;
        int y=0;
        boolean die=false;

        public FriendRightBullets(int x,int y){
            this.x=x+10;
            this.y=y;
            friendRightBullets.add(this);

        }
        public void run(){
            while (!die && isContinue){
                if(x<500 ){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.x+=10;
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }
                else {
                    lock.lock();
                    friendRightBullets.remove(this);
                    die=true;
                    lock.unlock();
                }

            }
        }
        public void dissappear(){
            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<=friends.get(i).y+10 && x>=friends.get(i).x && x<=friends.get(i).x+10){
                    lock.lock();
                    die=true;
                    friendRightBullets.remove(this);
                    lock.unlock();
                }
            }
            if(y>=AirCraft.y && y<=AirCraft.y+10 && x>=AirCraft.x && x<=AirCraft.x+10){
                lock.lock();
                die=true;
                friendRightBullets.remove(this);
                lock.unlock();
            }
        }

        public void defeat(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    lock.lock();
                    friendRightBullets.remove(this);
                    if(i<enemies.size()) {
                        enemies.get(i).exit = true;
                        enemies.remove(i);
                    }
                    if(enemies.size()==0){
                        message="Oyunu Kazandınız.";
                        isContinue=false;
                    }
                    lock.unlock();
                }
            }
        }
    }

    class FriendLeftBullets extends Thread {
        int x = 0;
        int y = 0;
        boolean die=false;

        public FriendLeftBullets(int x, int y) {
            this.x = x;
            this.y = y;
            friendLeftBullets.add(this);
        }

        public void run() {
            while (!die && isContinue) {
                if(x > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.x -= 10;
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }
                else {
                    lock.lock();
                    friendLeftBullets.remove(this);
                    die=true;
                    lock.unlock();
                }
            }

        }

        public void dissappear(){
            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<=friends.get(i).y+10 && x>=friends.get(i).x && x<=friends.get(i).x+10){
                    die=true;
                    lock.lock();
                    friendLeftBullets.remove(this);
                    lock.unlock();
                }
            }
            if(y>=AirCraft.y && y<=AirCraft.y+10 && x>=AirCraft.x && x<=AirCraft.x+10){
                die=true;
                lock.lock();
                friendLeftBullets.remove(this);
                lock.unlock();
            }
        }

        public void defeat(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    System.out.println("a");
                    enemies.get(i).exit=true;
                    die=true;
                    lock.lock();
                    friendLeftBullets.remove(this);
                    if(i<enemies.size()) {
                        enemies.get(i).exit = true;
                        enemies.remove(i);
                    }
                    if(enemies.size()==0){
                        message="Oyunu Kazandınız.";
                        isContinue=false;
                    }
                    lock.unlock();
                }
            }
        }
    }

    class AirCraftRightBullet extends Thread{
        int x=0;
        int y=0;
        boolean die=false;

        public AirCraftRightBullet(int x,int y){
            this.x=x+10;
            this.y=y;
            craftRightBullet.add(this);
        }

        public void run(){
            while (!die && isContinue){
                if(x<500 ){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.x+=10;
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }

                else {
                    lock.lock();
                    craftRightBullet.remove(this);
                    lock.unlock();
                    die=true;
                }

            }
        }
        public void dissappear(){
            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<=friends.get(i).y+10 && x>=friends.get(i).x && x<=friends.get(i).x+10){
                    die=true;
                    lock.lock();
                    craftRightBullet.remove(this);
                    lock.unlock();
                }
            }
        }

        public void defeat(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    lock.lock();
                    craftRightBullet.remove(this);
                    if(i<enemies.size()) {
                        enemies.get(i).exit = true;
                        enemies.remove(i);
                    }
                    if(enemies.size()==0){
                        message="Oyunu Kazandınız.";
                        isContinue=false;
                    }
                    lock.unlock();
                }
            }
        }
    }

    class AirCraftLeftBullet extends Thread {
        int x = 0;
        int y = 0;
        boolean die=false;

        public AirCraftLeftBullet(int x, int y) {
            this.x = x;
            this.y = y;
            craftLeftBullet.add(this);
        }

        public void run() {
            while (!die && isContinue) {
                if(x > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    this.x -= 10;
                    defeat();
                    dissappear();
                    paint(getGraphics());
                }
                else {
                    lock.lock();
                    craftLeftBullet.remove(this);
                    lock.unlock();
                    die=true;
                }
            }

        }

        public void dissappear(){
            for(int i=0; i<friends.size();i++){
                if(y>=friends.get(i).y && y<=friends.get(i).y+10 && x>=friends.get(i).x && x<=friends.get(i).x+10){
                    die=true;
                    lock.lock();
                    craftLeftBullet.remove(this);
                    lock.unlock();
                }
            }
        }

        public void defeat(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    lock.lock();
                    craftLeftBullet.remove(this);
                    if(i<enemies.size()) {
                        enemies.get(i).exit = true;
                        enemies.remove(i);
                    }
                    if(enemies.size()==0){
                        message="Oyunu Kazandınız.";
                        isContinue=false;
                    }
                    lock.unlock();
                }
            }
        }
    }
}
/*
public void defeat(){
            for(int i=0; i<enemies.size();i++){
                if(y>=enemies.get(i).y && y<=enemies.get(i).y+10 && x>=enemies.get(i).x && x<=enemies.get(i).x+10){
                    System.out.println("a");
                    die=true;
                    craftRightBullet.remove(this);
                    enemies.get(i).exit=true;
                    enemies.remove(i);
                    if(enemies.size()==0){
                        message="Oyunu Kazandınız.";
                        isContinue=false;
                    }
                }
            }
        }
 */