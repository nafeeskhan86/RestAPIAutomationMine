package pojo;

public class CreateArticleRequestBody {

    private CreateArticleRequest article;

    public CreateArticleRequestBody(CreateArticleRequest article) {
        this.article = article;
    }

    public CreateArticleRequest getArticle() {
        return article;
    }

    public void setArticle(CreateArticleRequest article) {
        this.article = article;
    }
}
