package com.shopmax.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarItem is a Querydsl query type for CarItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarItem extends EntityPathBase<CarItem> {

    private static final long serialVersionUID = -551220183L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarItem carItem = new QCarItem("carItem");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCart cart;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QCarItem(String variable) {
        this(CarItem.class, forVariable(variable), INITS);
    }

    public QCarItem(Path<? extends CarItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarItem(PathMetadata metadata, PathInits inits) {
        this(CarItem.class, metadata, inits);
    }

    public QCarItem(Class<? extends CarItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

