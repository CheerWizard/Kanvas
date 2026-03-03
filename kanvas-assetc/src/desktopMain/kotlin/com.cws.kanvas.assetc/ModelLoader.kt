package com.cws.kanvas.assetc

import com.cws.kanvas.math.Mat4
import com.cws.print.Print
import com.cws.std.memory.NativeBuffer
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AINode
import org.lwjgl.assimp.AIScene
import org.lwjgl.assimp.Assimp.*
import org.lwjgl.system.MemoryStack

data class Model(
    val meshes: ArrayList<Mesh> = ArrayList(),
)

data class Mesh(
    val vertexAttribute: List<VertexAttribute>,
    val vertexCount: Int,
    val vertices: NativeBuffer,
    val indexCount: Int,
    val indices: NativeBuffer,
)

enum class VertexAttribute {
    POS,
    UV,
    NORMAL,
    TANGENT,
    BONE_ID,
    BONE_WEIGHT,
}

data class Skeleton(
    val bones: HashMap<String, Bone> = HashMap(),
)

data class Bone(
    val id: Int,
    val name: String,
    val offset: Mat4,
)

//class ModelLoader {
//
//    companion object {
//        private const val TAG = "ModelLoader"
//    }
//
//    static void SetVertexBoneData(sVertex& vertex, int boneID, float weight)
//    {
//        for (int i = 0; i < 4; ++i)
//        {
//            if (vertex.BoneIDs[i] < 0)
//            {
//                vertex.BoneWeights[i] = weight;
//                vertex.BoneIDs[i] = boneID;
//                break;
//            }
//        }
//    }
//
//    static sVertex ParseVertex(aiMesh* mesh, u32 i)
//    {
//        sVertex vertex;
//
//        auto& pos = mesh->mVertices[i];
//        vertex.Position = { pos.x, pos.y, pos.z };
//
//        if (mesh->mTextureCoords[0]) {
//        auto& uv = mesh->mTextureCoords[0][i];
//        vertex.UV = { uv.x, uv.y };
//    }
//
//        auto& normal = mesh->mNormals[i];
//        vertex.Normal = { normal.x, normal.y, normal.z };
//
//        if (mesh->mTangents != nullptr)
//        {
//            auto& tangent = mesh->mTangents[i];
//            vertex.Tangent = { tangent.x, tangent.y, tangent.z };
//        }
//
//        return vertex;
//    }
//
//    fun load(filepath: String): Model? {
//        MemoryStack.stackPush().use { stack ->
//            val flags = aiProcess_Triangulate or
//                    aiProcess_FlipUVs or
//                    aiProcess_JoinIdenticalVertices or
//                    aiProcess_OptimizeMeshes or
//                    aiProcess_GenNormals or
//                    aiProcess_CalcTangentSpace
//
//            val scene = aiImportFile(filepath, flags)
//            val root = scene?.mRootNode()
//
//            if (scene == null || (scene.mFlags() and AI_SCENE_FLAGS_INCOMPLETE) == 1 || root == null) {
//                Print.e(TAG, "Failed to load model from $filepath")
//                return null
//            }
//
//            val model = Model()
//            parseMeshes(root, scene, model, flags)
//        }
//    }
//
//    private fun parseMeshes(node: AINode, scene: AIScene, model: Model, flags: Int) {
//        repeat(node.mNumMeshes()) { i ->
//            val sceneMeshes = scene.mMeshes()
//            val nodeMeshes = node.mMeshes()
//            if (sceneMeshes != null && nodeMeshes != null) {
//                val aiMesh = sceneMeshes[nodeMeshes[i]]
//                model.meshes.add(parseMesh(aiMesh))
//            }
//        }
//
//        repeat(node.mNumChildren()) { i ->
//            val children = node.mChildren()
//            if (children != null) {
//                parseMeshes(children[i], scene, model, flags)
//            }
//        }
//    }
//
//    private fun parseMesh(mesh: AIMesh): Mesh {
//        val mesh = Mesh(
//            vertices = NativeBuffer()
//        )
//
//    }
//
//    static sGeometry ParseMesh(aiMesh *mesh)
//    {
//        sGeometry geometry;
//        vector<u32> indices;
//
//        geometry.Vertices.resize(mesh->mNumVertices);
//
//        for (int i = 0 ; i < mesh->mNumVertices ; i++)
//        {
//            geometry.Vertices[i] = ParseVertex(mesh, i);
//        }
//
//        for (u32 i = 0 ; i < mesh->mNumFaces ; i++)
//        {
//            aiFace face = mesh->mFaces[i];
//            for (u32 j = 0 ; j < face.mNumIndices ; j++)
//            {
//                indices.push_back(face.mIndices[j]);
//            }
//        }
//
//        geometry.Indices.resize(indices.size());
//        memcpy(geometry.Indices.data(), indices.data(), indices.size() * sizeof(u32));
//
//        anim::sSkeleton skeleton;
//        auto& bones = skeleton.Bones;
//        int boneCounter = 0;
//        for (int i = 0; i < mesh->mNumBones; i++)
//        {
//            int boneID;
//            aiBone* bone = mesh->mBones[i];
//            string boneName = bone->mName.C_Str();
//
//            if (bones.find(boneName) == bones.end())
//            {
//                anim::sBone newBone;
//                newBone.ID = boneCounter;
//                newBone.Name = boneName;
//                newBone.Offset = cAssimpManager::ToMat4(bone->mOffsetMatrix);
//                bones.insert({ boneName, newBone });
//                boneID = boneCounter;
//                boneCounter++;
//            }
//
//            else {
//                boneID = bones[boneName].ID;
//            }
//
//            auto weights = bone->mWeights;
//            int numWeights = bone->mNumWeights;
//
//            for (int wi = 0; wi < numWeights; wi++)
//            {
//                int vertexId = weights[wi].mVertexId;
//                float weight = weights[wi].mWeight;
//                SetVertexBoneData(geometry.Vertices[vertexId], boneID, weight);
//            }
//        }
//
//        return geometry;
//    }
//
//}