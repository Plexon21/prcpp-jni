#include "Matrix.h"
#include "stdafx.h"

void multiply(jdouble * a, jdouble * b, jdouble * r, jint w, jint h, jint k) {
	for (jint y = 0; y < h; ++y) {
		jint index = y * w;
		for (jint x = 0; x < w; ++x, ++index) {
			jdouble tmp = 0.0;
			for (jint i = 0; i < k; ++i) {
				tmp += a[i + (y*k)] * b[x + (i*w)];
			}
			r[index] = tmp;
		}
	}
}

JNIEXPORT void JNICALL Java_Matrix_multiplyC(JNIEnv *env, jclass c, jdoubleArray a, jdoubleArray b, jdoubleArray r, jint i, jint j, jint k) {
	jboolean acopy, bcopy, rcopy;
	jdouble * pa = env->GetDoubleArrayElements(a, &acopy);
	jdouble * pb = env->GetDoubleArrayElements(b, &bcopy);
	jdouble * pr = env->GetDoubleArrayElements(r, &rcopy);

	multiply(pa, pb, pr, i, j, k);

	if (acopy == JNI_TRUE) { env->ReleaseDoubleArrayElements(a, pa, JNI_ABORT); }
	if (bcopy == JNI_TRUE) { env->ReleaseDoubleArrayElements(b, pb, JNI_ABORT); }
	if (rcopy == JNI_TRUE) { env->ReleaseDoubleArrayElements(r, pr, 0); }
}

JNIEXPORT void JNICALL Java_Matrix_powerC(JNIEnv *env, jclass c, jdoubleArray a, jdoubleArray b, jdoubleArray r, jint h, jint w, jint m, jint k) {
	jboolean acopy, bcopy, rcopy;
	jdouble * pa = env->GetDoubleArrayElements(a, &acopy);
	jdouble * pb = env->GetDoubleArrayElements(b, &bcopy);
	jdouble * pr = env->GetDoubleArrayElements(r, &rcopy);


	if (acopy == JNI_TRUE) { env->ReleaseDoubleArrayElements(a, pa, JNI_ABORT); }
	env->ReleaseDoubleArrayElements(r, pr, 0);
}