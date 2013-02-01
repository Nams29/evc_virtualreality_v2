package fr.istic.evc.client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.istic.evc.client.controller.CVirtualRealityClient;

public class PVirtualReality extends JFrame {
	
	private static final long serialVersionUID = -7459376830120915468L;
	
	private static final String FRAME_TITLE = "EVC - VirtualReality";

	private CVirtualRealityClient controller;
	
	private SimpleUniverse universe;
	
	private Canvas3D canvas3d;
	private BranchGroup scene;
	
	// Buttons
	private JButton rotateXPositiveBtn;
	private JButton rotateXNegativeBtn;
	private JButton rotateYPositiveBtn;
	private JButton rotateYNegativeBtn;
	private JButton rotateZPositiveBtn;
	private JButton rotateZNegativeBtn;
	private JButton moveForwardBtn;
	private JButton moveBackwardBtn;
	private JButton moveUpBtn;
	private JButton moveDownBtn;
	private JButton moveLeftBtn;
	private JButton moveRightBtn;
	
	// TextFields
	private JTextField absPosX;
	private JTextField absPosY;
	private JTextField absPosZ;
	private JTextField absRotX;
	private JTextField absRotY;
	private JTextField absRotZ;
	
	// Création objet
	private JTextField objName;
	private JTextField objShape;
	private JTextField objPosX;
	private JTextField objPosY;
	private JTextField objPosZ;
	private JTextField objRotX;
	private JTextField objRotY;
	private JTextField objRotZ;
	private JTextField objRot;
	private JTextField objSize;
	private JColorChooser objColors;
	private JButton createCube;
	
	public PVirtualReality(CVirtualRealityClient ctrl) {
		super(FRAME_TITLE);
		
		this.controller = ctrl;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);
		setLocationRelativeTo(null);
		
		this.initLayout();
		this.initPanelCreation();
		this.initListeners();
	}
	
	/**
	 * Initialize the graphic components
	 */
	private void initLayout() {
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		
		canvas3d = new Canvas3D(config);
		
		scene = createSceneGraph();
		
		this.universe = new SimpleUniverse(canvas3d);
		this.universe.getViewingPlatform().setNominalViewingTransform();
		this.universe.addBranchGraph(scene);

		JPanel leftPanel = new JPanel(new GridLayout(0, 1));
		moveForwardBtn = new JButton("Move Forward");
		moveBackwardBtn = new JButton("Move Backward");
		moveUpBtn = new JButton("Move Up");
		moveDownBtn = new JButton("Move Down");
		moveLeftBtn = new JButton("Move Left");
		moveRightBtn = new JButton("Move Right");
		
		leftPanel.add(moveForwardBtn);
		leftPanel.add(moveBackwardBtn);
		leftPanel.add(moveUpBtn);
		leftPanel.add(moveDownBtn);
		leftPanel.add(moveLeftBtn);
		leftPanel.add(moveRightBtn);
		
		JPanel absPosPanel = new JPanel(new FlowLayout());
		this.absPosX = new JTextField();
		this.absPosY = new JTextField();
		this.absPosZ = new JTextField();
		this.absPosX.setToolTipText("X");
		this.absPosY.setToolTipText("Y");
		this.absPosZ.setToolTipText("Z");
		this.absPosX.setColumns(3);
		this.absPosY.setColumns(3);
		this.absPosZ.setColumns(3);
		
		JButton absPosBtn = new JButton("Go");
		absPosBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.p2cMoveAbsolute(Float.valueOf(absPosX.getText()), 
										   Float.valueOf(absPosY.getText()), 
										   Float.valueOf(absPosZ.getText()));
			}
		});
		
		absPosX.setText("0");
		absPosY.setText("0");
		absPosZ.setText("0");
		
		absPosPanel.add(absPosX);
		absPosPanel.add(absPosY);
		absPosPanel.add(absPosZ);
		absPosPanel.add(absPosBtn);
		
		JPanel absRotPanel = new JPanel(new FlowLayout());
		this.absRotX = new JTextField();
		this.absRotY = new JTextField();
		this.absRotZ = new JTextField();
		this.absRotX.setToolTipText("X");
		this.absRotY.setToolTipText("Y");
		this.absRotZ.setToolTipText("Z");
		this.absRotX.setColumns(3);
		this.absRotY.setColumns(3);
		this.absRotZ.setColumns(3);

		JButton absRotBtn = new JButton("Go");
		absRotBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.p2cRotateAbsolute(Float.valueOf(absRotX.getText()), 
											 Float.valueOf(absRotY.getText()), 
											 Float.valueOf(absRotZ.getText()));
			}
		});
		
		absRotX.setText("0");
		absRotY.setText("0");
		absRotZ.setText("0");
		
		absRotPanel.add(absRotX);
		absRotPanel.add(absRotY);
		absRotPanel.add(absRotZ);
		absRotPanel.add(absRotBtn);
		
		JPanel absPanel = new JPanel(new GridLayout(2, 1));
		absPanel.add(absPosPanel);
		absPanel.add(absRotPanel);
		
		leftPanel.add(absPanel);
		
		JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
		rotateXNegativeBtn = new JButton("Rotate Upward");
		rotateXPositiveBtn = new JButton("Rotate Downward");
		rotateYNegativeBtn = new JButton("Rotate Right");
		rotateYPositiveBtn = new JButton("Rotate Left");
		rotateZNegativeBtn = new JButton("Roll Right");
		rotateZPositiveBtn = new JButton("Roll Left");
		bottomPanel.add(rotateXPositiveBtn);
		bottomPanel.add(rotateXNegativeBtn);
		bottomPanel.add(rotateYPositiveBtn);
		bottomPanel.add(rotateYNegativeBtn);
		bottomPanel.add(rotateZPositiveBtn);
		bottomPanel.add(rotateZNegativeBtn);
		
		this.setLayout(new BorderLayout());
		this.add(leftPanel, BorderLayout.WEST);
		this.add(canvas3d, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	private void initPanelCreation() {
		Dimension d = new Dimension( 200, 24 );
		
		objName = new JTextField();
		objName.setPreferredSize(d);
		objName.setMaximumSize(d);
		
		objShape = new JTextField();
		objShape.setPreferredSize(d);
		objShape.setMaximumSize(d);
		
		JPanel objPosPanel = new JPanel(new FlowLayout());
		objPosX = new JTextField();
		objPosY = new JTextField();
		objPosZ = new JTextField();
		objPosPanel.add(objPosX);
		objPosPanel.add(objPosY);
		objPosPanel.add(objPosZ);
		objPosX.setText("0");
		objPosY.setText("0");
		objPosZ.setText("0");
		objPosX.setColumns(3);
		objPosY.setColumns(3);
		objPosZ.setColumns(3);
		
		JPanel objRotPanel = new JPanel(new FlowLayout());
		objRotX = new JTextField();
		objRotY = new JTextField();
		objRotZ = new JTextField();
		objRot = new JTextField();
		objRotPanel.add(objRotX);
		objRotPanel.add(objRotY);
		objRotPanel.add(objRotZ);
		objRotPanel.add(objRot);
		objRotX.setText("0");
		objRotY.setText("0");
		objRotZ.setText("0");
		objRot.setText("0");
		objRotX.setColumns(3);
		objRotY.setColumns(3);
		objRotZ.setColumns(3);
		objRot.setColumns(3);
		
		objSize = new JTextField();
		objSize.setPreferredSize(d);
		objSize.setMaximumSize(d);
		objSize.setText("0.1");
		
		objColors = new JColorChooser(Color.WHITE);
		objColors.setPreviewPanel(new JPanel());

		createCube = new JButton("Creer cube");
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(new JLabel("Nom de l'objet : "));
		rightPanel.add(objName);
		rightPanel.add(new JLabel("URL de la forme VRML : "));
		rightPanel.add(objShape);
		rightPanel.add(new JLabel("Position: "));
		rightPanel.add(objPosPanel);
		rightPanel.add(new JLabel("Rotation: "));
		rightPanel.add(objRotPanel);
		rightPanel.add(new JLabel("Taille: "));
		rightPanel.add(objSize);
		rightPanel.add(new JLabel("Couleur : "));
		rightPanel.add(objColors);
		rightPanel.add(createCube);

		this.add(rightPanel, BorderLayout.EAST);
	}
	
	/**
	 * Initialize the listeners
	 */
	private void initListeners() {
		moveForwardBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(0, 0, -0.1f);
			}
		});
		moveBackwardBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(0, 0, 0.1f);
			}
		});
		moveUpBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(0, 0.1f, 0);
			}
		});
		moveDownBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(0, -0.1f, 0);
			}
		});
		moveLeftBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(-0.1f, 0, 0);
			}
		});
		moveRightBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cMove(0.1f, 0, 0);
			}
		});

		rotateXPositiveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(0.1f, 0, 0);
			}
		});
		rotateXNegativeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(-0.1f, 0, 0);
			}
		});
		rotateYPositiveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(0, 0.1f, 0);
			}
		});
		rotateYNegativeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(0, -0.1f, 0);
			}
		});
		rotateZPositiveBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(0, 0, 0.1f);
			}
		});
		rotateZNegativeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.p2cRotate(0, 0, -0.1f);
			}
		});
		
		createCube.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createObject();
			}
		});
	}
	
	/**
	 * Create the 3D elements
	 * @return the branch group created
	 */
	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();
				
		// Behaviour
		objRoot.addChild(new PickRotateBehavior(objRoot, canvas3d, 
				new BoundingSphere(new Point3d(0, 0, 0), 0.2)));
		objRoot.addChild(new PickTranslateBehavior(objRoot, canvas3d, 
				new BoundingSphere(new Point3d(0, 0, 0), 0.2)));
		objRoot.addChild(new PickZoomBehavior(objRoot, canvas3d, 
				new BoundingSphere(new Point3d(0, 0, 0), 0.2)));
		
		objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		
		// Compile
		objRoot.compile();
		
		return objRoot;
	}
	
	/**
	 * Move relatively to the current point of view
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void c2pMoveRelative(float dx, float dy, float dz) {
		TransformGroup vpTrans = universe.getViewingPlatform().getViewPlatformTransform();
		
		Transform3D oldT3D = new Transform3D();
		vpTrans.getTransform(oldT3D);

		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		
		Transform3D localT3D = new Transform3D();
		localT3D.setTranslation(translate);
		
		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		
		vpTrans.setTransform(newT3D);
	}
	
	/**
	 * Move on an absolute position
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void c2pMoveAbsolute(float dx, float dy, float dz) {
		TransformGroup vpTrans = universe.getViewingPlatform().getViewPlatformTransform();
		
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		
		Transform3D t3d = new Transform3D();
		vpTrans.getTransform(t3d);
		t3d.setTranslation(translate);
		vpTrans.setTransform(t3d);
	}
	
	/**
	 * Orientate relatively to the current point of view
	 * @param h
	 * @param p
	 * @param r
	 */
	public void c2pOrientRelative(float h, float p, float r) {
		TransformGroup vpTrans = universe.getViewingPlatform().getViewPlatformTransform();
		
		Transform3D oldT3D = new Transform3D();
		vpTrans.getTransform(oldT3D);
		
		Vector3d rotate = new Vector3d();
		rotate.set(h, p, r);
		
		Transform3D localT3D = new Transform3D();
		localT3D.setEuler(rotate);
		
		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		
		vpTrans.setTransform(newT3D);
	}
	
	/**
	 * Orientate on an absolute angle
	 * @param h
	 * @param p
	 * @param r
	 */
	public void c2pOrientAbsolute(float h, float p, float r) {
		TransformGroup vpTrans = universe.getViewingPlatform().getViewPlatformTransform();
		
		Vector3d rotate = new Vector3d();
		rotate.set(h, p, r);
		
		Transform3D t3d = new Transform3D();
		vpTrans.getTransform(t3d);
		
		Vector3d translate = new Vector3d();
		t3d.get(translate);
		t3d.setEuler(rotate);
		t3d.setTranslation(translate);
		
		vpTrans.setTransform(t3d);
	}
	
	public void c2pMoveViewpoint(Transform3D t) {
		TransformGroup vpTrans = universe.getViewingPlatform().getViewPlatformTransform();
		
		vpTrans.setTransform(t);
	}
	
	public void c2pCreateObject(PVirtualObject object) {
		BranchGroup objRoot = new BranchGroup();
		
		object.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		object.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		objRoot.addChild(object);
		
		objRoot.compile();
		
		//this.universe.addBranchGraph(objRoot);
		this.scene.addChild(objRoot);
	}
	
	private void createObject() {
		String n = objName.getText();
		
		if (!n.isEmpty()) {
			Vector3d v = new Vector3d(
					Double.valueOf(objPosX.getText()), 
					Double.valueOf(objPosY.getText()), 
					Double.valueOf(objPosZ.getText()));
			Quat4d r = new Quat4d(
					Double.valueOf(objRotX.getText()), 
					Double.valueOf(objRotY.getText()),
					Double.valueOf(objRotZ.getText()),
					Double.valueOf(objRot.getText()));
			
			if ( Double.isNaN(r.x) || Double.isNaN(r.y) || Double.isNaN(r.z) || Double.isNaN(r.w)) {
				r = new Quat4d();
			}
			
			r.normalize();
			
			String size = objSize.getText();
			
			if (size.isEmpty()) size = String.valueOf(0.1);
			
			controller.p2cCreateObject(n, Float.valueOf(size), objColors.getColor(), v, r, objShape.getText());
		}
	}
}
