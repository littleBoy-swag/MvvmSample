package cn.nqy.annotation_processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import cn.nqy.annotation.NqyRouter;

/**
 * 注解处理器
 */
@AutoService(Processor.class) // 编译期绑定
@SupportedAnnotationTypes({"cn.nqy.annotation.NqyRouter"}) // 表示我要处理哪个注解
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NqyRouterProcessor extends AbstractProcessor {

    // 操作Element的工具类，函数，类，属性都是Element
    private Elements elementsTool;

    // 类信息的工具类
    private Types typesTool;

    // 编译器打印日志
    private Messager messager;

    // java文件生成器
    private Filer filer;

    // 做一些初始化工作
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsTool = processingEnv.getElementUtils();
        typesTool = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            messager.printMessage(Diagnostic.Kind.NOTE, "没有发现被NqyRouter注解的类");
            return false; // 表示注解处理器没有工作
        }
        // 获取被NqyRouter注解的类信息
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(NqyRouter.class);
        for (Element element : elements) {
            // 获取类节点，获取包节点
            PackageElement packageElement = elementsTool.getPackageOf(element);
            String packageName = packageElement.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "PackageName is " + packageName);

            // 获取全类名
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被NqyRouter注解的类：" + className);
            NqyRouter nqyRouter = element.getAnnotation(NqyRouter.class);

            // 生成一段代码
            generateHelloWorld();
        }
        return false;
    }

    private void generateHelloWorld() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}