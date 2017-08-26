/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ssm.service.spider.douban;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class writeFile {

    private String path;

    //写文件
    static void contentToTxt(String filePath, String content) {
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.println("文件存在");
            } else {
                System.out.println("文件不存在");
                f.createNewFile();// 不存在则创建  
            }
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeMovie(String movie, String moviename) {
        path = ".\\File\\Movie\\" + moviename + ".txt";
        contentToTxt(path, movie);
    }

}

class ReadFile {

    private FileReader fr = null;
    private BufferedReader br = null;

    Set<String> readmoviename(String source) {
        Set<String> result = new HashSet<String>();
        try {
            fr = new FileReader(source);
            br = new BufferedReader(fr);
            String str = null;
            while ((str = br.readLine()) != null) {
                if (!str.isEmpty()) {
                    result.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}

class getPage {

    //获取页面
    String SendGet(String url, String decode) {
        String result = "";
        BufferedReader in = null;
        System.out.println(url);
        try {
            URL realURL = new URL(url);
            URLConnection conn = realURL.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), decode));
            String line;
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("Error!!" + e);
            e.printStackTrace(); //在命令行打印异常信息在程序中出错的位置及原因
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}

class Movie extends Thread {

    static getPage getpage = new getPage();
    static writeFile file = new writeFile();
    static ReadFile readfile = new ReadFile();
    private Pattern pattern;
    private Matcher matcher;
    private boolean result;
    private String url;
    private String content;
    private String m_name = "";
    private String m_id = "";
    private String director = "";
    private String writer = "";
    private String actor = "";
    private String type = "";
    private String area = "";
    private String date = "";
    private String intro = "";
    private String writecontent = "";


    String getDictor(String content) {
        pattern = Pattern.compile("<span class='pl'>导演</span>:(.*?)</span><br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            director = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            director = "";
        }
        return director;
    }

    String getWriter(String content) {
        pattern = Pattern.compile("<span class='pl'>编剧</span>:(.*?)</span><br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            writer = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            writer = "";
        }
        return writer;
    }

    String getActor(String content) {
        pattern = Pattern.compile("<span class='pl'>主演</span>:(.*?)</span><br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            actor = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            actor = "";
        }
        return actor;
    }

    String getType(String content) {
        pattern = Pattern.compile("<span class=\"pl\">类型:(.*?)</span><br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            type = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            type = "";
        }
        return type;
    }

    String getArea(String content) {
        pattern = Pattern.compile("<span class=\"pl\">制片国家/地区:(.*?)<br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            area = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            area = "";
        }
        return area;
    }

    String getDate(String content) {
        pattern = Pattern.compile("<span class=\"pl\">上映日期:(.*?)</span><br/>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            date = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            date = "";
        }
        return date;
    }

    String getIntro(String content) {
        pattern = Pattern.compile("v:summary.*?>(.*?)</span>");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            intro = matcher.group(1).replaceAll("<.*?>", "").trim();
        } else {
            intro = "";
        }
        return intro;
    }

    List getSingleMovie(String id, String moviename) {
        //获取单部电影信息
        List m_list = new ArrayList();
        List score_list = new ArrayList();
        url = "https://movie.douban.com/subject/" + id + "/?tag=热门&from=gaia";
        content = "";
        content = getpage.SendGet(url, "utf-8");
        if (content != "") {
            getDictor(content);
            writecontent = "";
            director = getDictor(content);
            writer = getWriter(content);
            actor = getActor(content);
            type = getType(content);
            area = getArea(content);
            date = getDate(content);
            intro = getIntro(content);
            m_list.add(director);
            m_list.add(writer);
            m_list.add(actor);
            m_list.add(type);
            m_list.add(area);
            m_list.add(date);
            m_list.add(intro);
            System.out.println("导演: " + director);
            System.out.println("编剧: " + writer);
            System.out.println("主演: " + actor);
            System.out.println("类型: " + type);
            System.out.println("制片国家/地区: " + area);
            System.out.println("上映日期: " + date);
            System.out.println("剧情简介: " + intro);

            writecontent = id + "`" + director + "`" + writer + "`" + actor + "`" + type + "`" + area + "`" + date + "`" + intro;
            file.writeMovie(writecontent, moviename);

        } else {
            System.out.println("获取失败!   " + id + moviename);
        }
        return m_list;
    }

    String getMovieID(String movieTitle) {
        url = "https://movie.douban.com/subject_search?search_text=" + movieTitle.replace(" ", "+");
        content = "";
        content = getpage.SendGet(url, "utf-8");
        if (content != "") {
            pattern = Pattern.compile("<a class=\"nbg\" href=\"https://movie.douban.com/subject/(.*?)/.*?title=\"(.*?)\"");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                m_id = matcher.group(1);
                m_name = matcher.group(2);
                System.out.println("电影id: " + m_id);
                System.out.println("电影名: " + m_name);
            }
            return m_id + "`" + m_name;
        } else {
            return "";
        }
    }

    Set<String> getSingleMovieName(String path) {
        Set<String> filename = readfile.readmoviename(path);
        for (String s : filename) {
            System.out.println("s:" + s);
        }
        return filename;
    }

    void getMovieInformation(String path) {
        Set<String> filename = getSingleMovieName(path);
        for (String moviename : filename) {
            String[] movie = getMovieID(moviename).split("`");
            if (movie.length >= 2) {
                m_id = movie[0];
                m_name = movie[1];
                System.out.println("movieID:    " + m_id);
                getSingleMovie(m_id, m_name);

            } else {
                System.out.println("movieID:    为空，搜索不到！");
            }
        }
    }

}

public class crawler {

    public static void main(String[] args) throws InterruptedException, IOException {

        Movie movie = new Movie();
        movie.getMovieInformation("./File/电影目录.txt");

    }
}
